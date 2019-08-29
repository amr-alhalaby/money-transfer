package com.revoult.moneytransfer.model;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.dto.AccountResponse;
import com.revoult.moneytransfer.exception.BusinessException;
import com.revoult.moneytransfer.exception.ServiceException;
import com.revoult.moneytransfer.exception.ValidationException;

public class Account {

	
	private volatile BigDecimal balance;
	private Integer id;
	private final Lock lock=new ReentrantLock();
	
	public Account() {
		super();
	}

	public Account(BigDecimal balance) {
		super();
		setBalance(balance);
	}
	public Account(Integer id,BigDecimal balance) {
		super();
		setBalance(balance);
		this.id = id;
	}
	
	public Lock getLock() {
		return lock;
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
	public void setBalance(BigDecimal bal) {
		validateAmount(bal);
		this.balance = bal;
	}
	
	public void deposite(BigDecimal amount) {
		validateAmount(amount);
		setBalance(amount.add(getBalance()));		
	}
	
	public void withdraw(BigDecimal amount) {
		validateAmount(amount);
		if (amount.compareTo(getBalance()) > 0 ) throw new BusinessException(ErrorCodes.INSUFFICIENT_BALANCE,"insufficient balance "+amount);
		setBalance(getBalance().subtract(amount));
		
	}
	
	private void validateAmount(BigDecimal amount) {
		if (amount ==null|| amount.doubleValue() < 0 ) throw new ValidationException(ErrorCodes.INVALID_AMOUNT,"invalid amount :"+amount);
	}
	
	public AccountResponse getAccResponse(){
		return new AccountResponse(getId(),getBalance());
	}
	
	@Override
	public String toString() {
		return "Account [balance=" + balance + ", id=" + id + "]";
	}
	
	
	
	
	
	
}
