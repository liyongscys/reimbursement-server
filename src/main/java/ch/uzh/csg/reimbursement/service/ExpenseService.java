package ch.uzh.csg.reimbursement.service;

import static ch.uzh.csg.reimbursement.model.ExpenseState.DRAFT;
import static ch.uzh.csg.reimbursement.model.ExpenseState.REJECTED;
import static ch.uzh.csg.reimbursement.model.ExpenseState.TO_BE_ASSIGNED;
import static ch.uzh.csg.reimbursement.model.Role.FINANCE_ADMIN;
import static ch.uzh.csg.reimbursement.model.Role.PROF;
import static ch.uzh.csg.reimbursement.model.Role.USER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ch.uzh.csg.reimbursement.dto.AccessRights;
import ch.uzh.csg.reimbursement.dto.ExpenseUrlDto;
import ch.uzh.csg.reimbursement.dto.SearchExpenseDto;
import ch.uzh.csg.reimbursement.model.Document;
import ch.uzh.csg.reimbursement.model.Expense;
import ch.uzh.csg.reimbursement.model.ExpenseState;
import ch.uzh.csg.reimbursement.model.Role;
import ch.uzh.csg.reimbursement.model.Token;
import ch.uzh.csg.reimbursement.model.User;
import ch.uzh.csg.reimbursement.model.exception.AccessViolationException;
import ch.uzh.csg.reimbursement.model.exception.AssignViolationException;
import ch.uzh.csg.reimbursement.model.exception.ExpenseDeleteViolationException;
import ch.uzh.csg.reimbursement.model.exception.ExpenseNotFoundException;
import ch.uzh.csg.reimbursement.model.exception.PdfExportViolationException;
import ch.uzh.csg.reimbursement.repository.ExpenseRepositoryProvider;

@Service
@Transactional
public class ExpenseService {

	private final Logger LOG = LoggerFactory.getLogger(ExpenseService.class);

	@Autowired
	private ExpenseRepositoryProvider expenseRepository;

	@Autowired
	private UserResourceAuthorizationService authorizationService;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private PdfGenerationService pdfGenerationService;

	@Value("${reimbursement.token.epxenseItemAttachmentMobile.expirationInMilliseconds}")
	private int tokenExpirationInMilliseconds;

	public Expense create(String accounting) {
		User user = userService.getLoggedInUser();

		Expense expense = new Expense(user, new Date(), null, accounting, DRAFT);
		expenseRepository.create(expense);

		return expense;
	}

	public Set<Expense> findAllByUser(String uid) {
		return expenseRepository.findAllByUser(uid);
	}

	public Set<Expense> findAllByAssignedManager(User user) {
		return expenseRepository.findAllByAssignedManager(user.getUid());
	}

	public Set<Expense> findAllForFinanceAdmin(String uid) {
		Set<Expense> expenses;
		expenses = expenseRepository.findAllByState(TO_BE_ASSIGNED);
		expenses.addAll(expenseRepository.findAllByFinanceAdmin(uid));

		return expenses;
	}

	public Set<Expense> findAllByState(ExpenseState state) {
		return expenseRepository.findAllByState(state);
	}

	public Set<Expense> getAllReviewExpenses() {
		User user = userService.getLoggedInUser();

		if (user.getRoles().contains(PROF)) {
			return findAllByAssignedManager(user);
		} else if (user.getRoles().contains(FINANCE_ADMIN)) {
			return findAllForFinanceAdmin(user.getUid());
		} else {
			LOG.debug("The logged in user has no access to this expense");
			throw new AccessViolationException();
		}
	}

	public Set<Expense> findAllByCurrentUser() {
		User user = userService.getLoggedInUser();
		return findAllByUser(user.getUid());
	}

	public void updateExpense(String uid, String accounting) {
		Expense expense = findByUid(uid);

		if (authorizationService.checkEditAuthorization(expense)) {
			expense.setAccounting(accounting);
		} else {
			LOG.debug("The logged in user has no access to this expense");
			throw new AccessViolationException();
		}
	}

	public Expense findByUid(String uid) {
		Expense expense = expenseRepository.findByUid(uid);

		if (expense != null) {
			if (authorizationService.checkViewAuthorization(expense)) {
				return expense;
			} else {
				LOG.debug("The logged in user has no access to this expense");
				throw new AccessViolationException();
			}
		} else {
			return findByToken(uid);
		}
	}

	public Expense findByToken(String tokenUid) {
		Token token = tokenService.findByUid(tokenUid);
		Expense expense = findByUid(token.getContent());

		if (expense != null) {
			if (authorizationService.checkViewAuthorization(expense)) {
				return expense;
			} else {
				LOG.debug("The token has no access to this expense");
				throw new AccessViolationException();
			}
		} else {
			LOG.debug("Expense not found in database with uid: " + tokenUid);
			throw new ExpenseNotFoundException();
		}
	}

	public void delete(String uid) {
		Expense expense = findByUid(uid);

		if (expense.getState() == DRAFT || expense.getState() == REJECTED) {
			expenseRepository.delete(expense);
		} else {
			LOG.debug("Expense cannot be deleted in this state");
			throw new ExpenseDeleteViolationException();
		}
	}

	public void acceptExpense(String uid) {
		Expense expense = findByUid(uid);

		if (authorizationService.checkEditAuthorization(expense)) {
			if (authorizationService.checkAssignAuthorization(expense)) {
				expense.goToNextState();
			} else {
				LOG.debug("Expenses without expenseItems cannot be assigned.");
				throw new AssignViolationException();
			}
		} else {
			LOG.debug("The logged in user has no access to this expense");
			throw new AccessViolationException();
		}
	}

	public void assignExpenseToMe(String uid) {
		Expense expense = findByUid(uid);
		User user = userService.getLoggedInUser();

		if (authorizationService.checkEditAuthorization(expense)) {
			if (user != expense.getUser()) {
				expense.setFinanceAdmin(user);
				expense.goToNextState();
			} else {
				LOG.debug("The logged in user has no access to this expense");
				throw new AssignViolationException();
			}
		} else {
			LOG.debug("The logged in user has no access to this expense");
			throw new AccessViolationException();
		}
	}

	public void assignExpenseToProf(String uid) {
		Expense expense = findByUid(uid);
		User user = userService.getLoggedInUser();
		User financeAdmin = userService.findByUid("fadmin");

		if (authorizationService.checkEditAuthorization(expense)) {
			if (authorizationService.checkAssignAuthorization(expense)) {
				// If the prof wants to hand in an expense the expense is
				// directly assigned to the chief of finance_admins
				if (user.getRoles().contains(Role.PROF)) {
					// TODO The department manager should be set in the
					// application properties
					expense.setFinanceAdmin(financeAdmin);
					User manager = userService.findByUid("lauber");
					expense.setAssignedManager(manager);
					expense.goToNextState();
				} else {
					expense.setAssignedManager(user.getManager());
					expense.goToNextState();
				}
			} else {
				LOG.debug("Expenses without expenseItems cannot be assigned.");
				throw new AssignViolationException();
			}
		} else {
			LOG.debug("The logged in user has no access to this expense");
			throw new AccessViolationException();
		}
	}

	public void rejectExpense(String uid, String comment) {
		Expense expense = findByUid(uid);
		if (authorizationService.checkEditAuthorization(expense)) {
			expense.reject(comment);
		} else {
			LOG.debug("The logged in user has no access to this expense");
			throw new AccessViolationException();
		}
	}

	public AccessRights getAccessRights(String uid) {
		AccessRights rights = new AccessRights();
		Expense expense;

		try {
			expense = findByUid(uid);
			rights.setViewable(true);

			if (authorizationService.checkEditAuthorization(expense)) {
				rights.setEditable(true);
			} else {
				rights.setEditable(false);
			}

			if (authorizationService.checkSignAuthorization(expense)) {
				rights.setSignable(true);
			} else {
				rights.setSignable(false);
			}
		} catch (AccessViolationException e) {
			rights.setViewable(false);
			rights.setEditable(false);
			rights.setSignable(false);
		}
		return rights;
	}

	public Set<Expense> search(SearchExpenseDto dto) {
		String accountingText = "%";
		if (dto.getAccountingText() != null && !dto.getAccountingText().equals("")) {
			accountingText = "%" + dto.getAccountingText() + "%";
		}

		List<User> relevantUsers = new ArrayList<>();

		// search for the last name
		List<User> temporaryUsers;
		if (dto.getLastName() != null && !dto.getLastName().equals("")) {
			temporaryUsers = userService.findAllByLastName("%" + dto.getLastName() + "%");
		} else {
			temporaryUsers = userService.findAll();
		}

		// filter for the role
		if (dto.getRole() != null && !dto.getRole().equals("")) {
			Role role = null;
			try {
				role = Role.valueOf(dto.getRole());
			} catch (IllegalArgumentException e) {
				LOG.debug("Illegal role name, ignoring.");
			}
			if (role != null) {
				for (User user : temporaryUsers) {
					Set<Role> roles = user.getRoles();
					if (role == USER) {
						// if role is user, only the users and not admin/fadmin
						// etc are added
						if (roles.contains(role) && roles.size() == 1) {
							relevantUsers.add(user);
						}
					} else {
						if (roles.contains(role)) {
							relevantUsers.add(user);
						}
					}
				}
			}
		} else {
			relevantUsers = temporaryUsers;
		}

		return expenseRepository.search(relevantUsers, accountingText);
	}

	public Document setSignedPdf(String expenseUid, MultipartFile multipartFile) {
		Expense expense = findByUid(expenseUid);
		return expense.setPdf(multipartFile);
	}

	public Document getPdf(String uid) {
		Expense expense = findByUid(uid);
		if (expense.getExpensePdf() == null) {
			LOG.debug("The PDF for the expense has not been generated yet");
			throw new PdfExportViolationException();
		} else {
			return expense.getExpensePdf();
		}
	}

	public void generatePdf(String uid, String url) {
		Expense expense = findByUid(uid);
		ExpenseUrlDto dto = new ExpenseUrlDto(expense, url);
		expense.setPdf(pdfGenerationService.generatePdf(dto));
	}
}
