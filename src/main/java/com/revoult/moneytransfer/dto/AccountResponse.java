package com.revoult.moneytransfer.dto;

import java.math.BigDecimal;


public class AccountResponse extends Response {

	private  BigDecimal balance;
	private Integer id;

	public AccountResponse() {
		super();
	}


	public AccountResponse(Integer id, BigDecimal balance) {
		super();
		setBalance(balance);
		setId(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [balance=" + balance + ", id=" + id + "]";
	}
}
