package ch.uzh.csg.reimbursement.view;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.uzh.csg.reimbursement.model.Expense;
import ch.uzh.csg.reimbursement.repository.ExpenseRepository;

@Service
public class ExpenseResourceMapper {

	@Autowired
	ExpenseMapper expenseMapper;

	@Autowired
	ExpenseRepository expenseRepository;

	public ExpenseResourceView map(String uid) {

		Set<Expense> expenses = expenseRepository.findAllByUser(uid);
		Set<Expense> reviewExpenses = expenseRepository.findAllByAssignedManager(uid);
		ExpenseResourceView expenseResourceView = new ExpenseResourceView();
		expenseResourceView.setUid(uid);
		expenseResourceView.setMyExpenseItems(expenseMapper.mapExpense(expenses));
		expenseResourceView.setMyReviewExpenseItems(expenseMapper.mapReviewExpense(reviewExpenses));
		return expenseResourceView;
	}
}
