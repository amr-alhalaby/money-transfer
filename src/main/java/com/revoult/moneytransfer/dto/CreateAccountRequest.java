package com.revoult.moneytransfer.dto;

import java.math.BigDecimal;

public class CreateAccountRequest extends Request{
	private BigDecimal amount;

	
	
	public CreateAccountRequest() {
		super();
	}

	public CreateAccountRequest(BigDecimal amount) {
		super();
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CreateAccount [amount=" + amount + "]";
	}

		

}
