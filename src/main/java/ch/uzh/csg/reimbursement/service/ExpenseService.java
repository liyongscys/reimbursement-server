package ch.uzh.csg.reimbursement.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.csg.reimbursement.dto.CreateExpenseDto;
import ch.uzh.csg.reimbursement.dto.ExpenseDto;
import ch.uzh.csg.reimbursement.model.Expense;
import ch.uzh.csg.reimbursement.model.ExpenseState;
import ch.uzh.csg.reimbursement.model.Role;
import ch.uzh.csg.reimbursement.model.User;
import ch.uzh.csg.reimbursement.model.exception.ExpenseAccessViolationException;
import ch.uzh.csg.reimbursement.model.exception.ExpenseNotFoundException;
import ch.uzh.csg.reimbursement.repository.ExpenseRepositoryProvider;
import ch.uzh.csg.reimbursement.view.ExpenseResourceMapper;
import ch.uzh.csg.reimbursement.view.ExpenseResourceView;

@Service
@Transactional
public class ExpenseService {

	private final Logger LOG = LoggerFactory.getLogger(ExpenseService.class);

	@Autowired
	private ExpenseRepositoryProvider expenseRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ExpenseResourceMapper expenseResourceMapper;

	public Expense create(CreateExpenseDto dto) {
		User user = userService.getLoggedInUser();

		Expense expense = new Expense(user, new Date(), null, dto.getAccounting(), dto.getState());
		expenseRepository.create(expense);

		return expense;
	}

	public ExpenseResourceView findAllByUser(String uid) {
		return expenseResourceMapper.map(uid);
	}

	public ExpenseResourceView findAllByCurrentUser() {
		User user = userService.getLoggedInUser();
		return findAllByUser(user.getUid());
	}

	public void updateExpense(String uid, ExpenseDto dto) {
		Expense expense = findByUid(uid);
		// TODO Determine where financeAdmin will be defined
		User financeAdmin = null;
		if (userService.getLoggedInUser().getRoles().contains(Role.FINANCE_ADMIN)
				&& expense.getUser() != userService.getLoggedInUser()) {
			financeAdmin = userService.getLoggedInUser();
		}
		User assignedManager;
		if (dto.getState() == ExpenseState.ASSIGNED_TO_FINANCE_ADMIN) {
			assignedManager = null;
		} else {
			assignedManager = userService.findByUid(dto.getAssignedManagerUid());
		}
		expense.updateExpense(new Date(), financeAdmin, dto.getAccounting(), assignedManager, dto.getState());
	}

	public Expense findByUid(String uid) {
		Expense expense = expenseRepository.findByUid(uid);

		if (expense == null) {
			LOG.debug("Expense not found in database with uid: " + uid);
			throw new ExpenseNotFoundException();
		} else if ((expense.getState() == ExpenseState.CREATED || expense.getState() == ExpenseState.REJECTED)
				&& expense.getUser() != userService.getLoggedInUser()) {
			LOG.debug("The logged in user has no access to this expense");
			throw new ExpenseAccessViolationException();
		} else if ((expense.getState() != ExpenseState.CREATED && expense.getState() != ExpenseState.REJECTED &&
				expense.getState() != ExpenseState.ASSIGNED_TO_FINANCE_ADMIN)
				&& expense.getAssignedManager() != userService.getLoggedInUser()) {
			LOG.debug("Expense not assigned to logged in user");
			throw new ExpenseAccessViolationException();
		} else if (expense.getState() == ExpenseState.ASSIGNED_TO_FINANCE_ADMIN
				&& !(userService.getLoggedInUser().getRoles().contains(Role.FINANCE_ADMIN))
				&& expense.getUser() == userService.getLoggedInUser()) {
			LOG.debug("The logged in user has no access to this expense");
			throw new ExpenseAccessViolationException();
		}
		return expense;
	}
}
