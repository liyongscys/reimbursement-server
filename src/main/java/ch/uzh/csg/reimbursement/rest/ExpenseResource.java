package ch.uzh.csg.reimbursement.rest;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.csg.reimbursement.dto.ExpenseDto;
import ch.uzh.csg.reimbursement.model.Expense;
import ch.uzh.csg.reimbursement.service.ExpenseService;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/expense")
@Api(value = "Expense", description = "Expense related actions")
public class ExpenseResource {

	@Autowired
	private ExpenseService expenseService;

	@RequestMapping(method = POST)
	@ApiOperation(value = "Create new expense", notes = "Creates a new expense when called with the correct arguments.")
	@ResponseStatus(OK)
	public void createExpense(@RequestBody ExpenseDto dto) {
		expenseService.create(dto);
	}

	@RequestMapping(method = GET)
	@ApiOperation(value = "Find all expenses for a given user", notes = "Finds all expenses that were created by the user.")
	@ResponseStatus(OK)
	public List<Expense> getAllExpenses() {
		return expenseService.findAll();
	}

	@RequestMapping(value = "/{uid}", method = PUT)
	@ApiOperation(value = "Update the expense with the given uid", notes = "Updates the expense with the given uid.")
	@ResponseStatus(OK)
	public void updateExpense(@PathVariable("uid") String uid, @RequestBody ExpenseDto dto) {
		expenseService.updateExpense(uid, dto);
	}

	@RequestMapping(value = "/{uid}", method = DELETE)
	@ApiOperation(value = "Remove an expense", notes = "Removes the expense with the given id.")
	@ResponseStatus(OK)
	public void removeExpense(@PathVariable("uid") String uid) {
		expenseService.removeByUid(uid);
	}
}
