package ch.uzh.csg.reimbursement.service;

import static ch.uzh.csg.reimbursement.model.ExpenseState.ARCHIVED;
import static ch.uzh.csg.reimbursement.model.ExpenseState.ASSIGNED_TO_FINANCE_ADMIN;
import static ch.uzh.csg.reimbursement.model.ExpenseState.ASSIGNED_TO_MANAGER;
import static ch.uzh.csg.reimbursement.model.ExpenseState.DRAFT;
import static ch.uzh.csg.reimbursement.model.ExpenseState.PRINTED;
import static ch.uzh.csg.reimbursement.model.ExpenseState.REJECTED;
import static ch.uzh.csg.reimbursement.model.ExpenseState.SIGNED;
import static ch.uzh.csg.reimbursement.model.ExpenseState.TO_BE_ASSIGNED;
import static ch.uzh.csg.reimbursement.model.ExpenseState.TO_SIGN_BY_FINANCE_ADMIN;
import static ch.uzh.csg.reimbursement.model.ExpenseState.TO_SIGN_BY_MANAGER;
import static ch.uzh.csg.reimbursement.model.ExpenseState.TO_SIGN_BY_USER;
import static ch.uzh.csg.reimbursement.model.Role.DEPARTMENT_MANAGER;
import static ch.uzh.csg.reimbursement.model.Role.FINANCE_ADMIN;
import static ch.uzh.csg.reimbursement.model.Role.PROF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.uzh.csg.reimbursement.model.Expense;
import ch.uzh.csg.reimbursement.model.ExpenseItem;
import ch.uzh.csg.reimbursement.model.ExpenseState;
import ch.uzh.csg.reimbursement.model.Token;
import ch.uzh.csg.reimbursement.model.User;

@Service
@Transactional
public class UserResourceAuthorizationService {

	@Autowired
	private UserService userService;

	public boolean checkEditAuthorization(Expense expense) {
		return checkEditAuthorization(expense, userService.getLoggedInUser());
	}

	public boolean checkEditAuthorization(ExpenseItem expenseItem) {
		return checkEditAuthorization(expenseItem.getExpense());
	}

	public boolean checkEditAuthorizationMobile(Expense expense, Token token) {
		return checkEditAuthorization(expense, token.getUser());
	}

	private boolean checkEditAuthorization(Expense expense, User user) {
		if ((expense.getState().equals(DRAFT) || expense.getState().equals(REJECTED)) && expense.getUserUid().equals(user.getUid())) {
			return true;
		} else if (expense.getState().equals(ASSIGNED_TO_MANAGER) && expense.getAssignedManager() != null
				&& expense.getAssignedManager().getUid().equals(user.getUid())) {
			return true;
		} else if (((expense.getState().equals(TO_BE_ASSIGNED) || expense.getState().equals(PRINTED) || expense.getState().equals(ARCHIVED)) &&
				user.getRoles().contains(FINANCE_ADMIN) && user.getUid() != expense.getUserUid()) ||
				(expense.getState().equals(ASSIGNED_TO_FINANCE_ADMIN) && expense.getFinanceAdmin() != null
				&& expense.getFinanceAdmin().getUid().equals(user.getUid()))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkViewAuthorization(ExpenseItem expenseItem) {
		return checkViewAuthorization(expenseItem.getExpense());
	}

	public boolean checkViewAuthorizationMobile(ExpenseItem expenseItem, Token token) {
		return checkViewAuthorization(expenseItem.getExpense(), token.getUser());
	}

	public boolean checkViewAuthorizationWithoutUser(Expense expense) {
		if (expense.getState().equals(PRINTED) || expense.getState().equals(ARCHIVED)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkViewAuthorization(Expense expense) {
		return checkViewAuthorization(expense, userService.getLoggedInUser());
	}

	private boolean checkViewAuthorization(Expense expense, User user) {
		if (expense.getUserUid().equals(user.getUid())) {
			return true;
		} else if (expense.getAssignedManager() != null && expense.getAssignedManager().getUid().equals(user.getUid())) {
			return true;
		} else if (user.getRoles().contains(FINANCE_ADMIN)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkSignAuthorization(Expense expense) {
		User user = userService.getLoggedInUser();
		if (expense.getState().equals(TO_SIGN_BY_USER) && expense.getUserUid().equals(user.getUid())) {
			return true;
		} else if (expense.getState().equals(TO_SIGN_BY_MANAGER) && expense.getAssignedManager().getUid().equals(user.getUid())) {
			return true;
		} else if (expense.getState().equals(TO_SIGN_BY_FINANCE_ADMIN) && expense.getFinanceAdmin().getUid().equals(user.getUid())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkDigitalSignatureDecision(Expense expense) {
		User user = userService.getLoggedInUser();
		if (expense.getUser().getUid().equals(user.getUid()) && expense.getState().equals(TO_SIGN_BY_USER)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkAssignAuthorization(Expense expense) {
		User user = userService.getLoggedInUser();
		if (!(user.getRoles().contains(DEPARTMENT_MANAGER) || user.getRoles().contains(FINANCE_ADMIN) ||
				user.getRoles().contains(PROF)) && !expense.getExpenseItems().isEmpty()) {
			return true;
		} else if (!expense.getExpenseItems().isEmpty() && projectFieldsSet(expense)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkPdfGenerationAuthorization(Expense expense) {
		User user = userService.getLoggedInUser();
		if ((expense.getState().equals(SIGNED) || expense.getState().equals(ExpenseState.TO_SIGN_BY_USER))
				&& expense.getUser().getUid().equals(user.getUid())) {
			return true;
		} else {
			return false;
		}
	}

	private boolean projectFieldsSet(Expense expense) {
		boolean allProjectFieldsSet = true;
		for (ExpenseItem expenseItem : expense.getExpenseItems()) {
			if (expenseItem.getProject() == null) {
				allProjectFieldsSet = false;
			}
		}
		return allProjectFieldsSet;
	}

	public boolean checkRejectAuthorization(Expense expense) {
		User user = userService.getLoggedInUser();
		if (expense.getAssignedManager() != null && expense.getAssignedManager().getUid().equals(user.getUid())
				&& expense.getState().equals(ASSIGNED_TO_MANAGER)) {
			return true;
		} else if (user.getRoles().contains(FINANCE_ADMIN)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkArchiveAuthorization(Expense expense) {
		User user = userService.getLoggedInUser();
		if(expense.getUserUid().equals(user.getUid()) && expense.getState().equals(PRINTED)) {
			return true;
		} else {
			return false;
		}
	}
}