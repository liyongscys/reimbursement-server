package ch.uzh.csg.reimbursement.server.dto;

import lombok.Data;

@Data
public class CroppingDto {

	private int width;
	private int height;
	private int top;
	private int left;

}
