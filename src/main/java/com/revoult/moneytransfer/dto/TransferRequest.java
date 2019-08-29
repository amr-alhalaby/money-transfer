package com.revoult.moneytransfer.dto;

import java.math.BigDecimal;

public class TransferRequest extends Request{
	
	
	private BigDecimal amount;
	private Integer from;
	private Integer to;

	
	
	public TransferRequest() {
		super();
	}

	public TransferRequest(BigDecimal amount) {
		super();
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getFrom() {
		return from;
	}

	public void setFrom(Integer from) {
		this.from = from;
	}

	public Integer getTo() {
		return to;
	}

	public void setTo(Integer to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "TransferRequest [amount=" + amount + ", from=" + from + ", to=" + to + "]";
	}


	


		

}
