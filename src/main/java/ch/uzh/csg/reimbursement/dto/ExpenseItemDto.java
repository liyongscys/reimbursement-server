package ch.uzh.csg.reimbursement.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ExpenseItemDto {

	private String expenseUid;
	private Date date;
	private String state;
	private String costCategoryUid;
	private String reason;
	private String currency;
	private double exchangeRate;
	private double amount;
	private String project;
}