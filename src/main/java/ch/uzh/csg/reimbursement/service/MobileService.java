package ch.uzh.csg.reimbursement.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ch.uzh.csg.reimbursement.model.Expense;
import ch.uzh.csg.reimbursement.model.ExpenseItem;
import ch.uzh.csg.reimbursement.model.ExpenseItemAttachment;
import ch.uzh.csg.reimbursement.model.Token;
import ch.uzh.csg.reimbursement.model.User;
import ch.uzh.csg.reimbursement.model.exception.TokenExpiredException;
import ch.uzh.csg.reimbursement.model.exception.TokenNotFoundException;

@Service
@Transactional
public class MobileService {

	@Autowired
	private UserService userService;

	@Autowired
	private ExpenseItemService expenseItemService;

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private TokenService tokenService;

	@Value("${reimbursement.token.signatureMobile.expirationInMilliseconds}")
	private int expirationInMilliseconds;

	public void createSignature(String tokenString, MultipartFile file) {
		Token token = tokenService.findByUid(tokenString);
		checkValidity(token);
		User user = token.getUser();
		userService.addSignature(user, file);
		tokenService.delete(token);
	}

	public String createExpenseItemAttachment(String tokenString, MultipartFile file) {
		Token token = tokenService.findByUid(tokenString);
		checkValidity(token);
		ExpenseItemAttachment expenseItemAttachment = expenseItemService.setAttachmentMobile(token, file);
		tokenService.delete(token);
		// TODO Check if token is really deleted
		return expenseItemAttachment.getUid();
	}

	public Expense getExpenseByToken(String tokenString) {
		Token token = tokenService.findByUid(tokenString);
		checkValidity(token);
		Expense expense = expenseService.findByToken(token);
		tokenService.delete(token);
		return expense;
	}

	public Set<ExpenseItem> getExpenseItemsByToken(String tokenString) {
		Expense expense = getExpenseByToken(tokenString);
		return expenseItemService.getExpenseItemsByExpenseUid(expense.getUid());
	}

	private void checkValidity(Token token) {
		if (token == null) {
			throw new TokenNotFoundException();
		}
		if (token.isExpired(expirationInMilliseconds)) {
			throw new TokenExpiredException();
		}
	}
}
