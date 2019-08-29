package com.revoult.moneytransfer.dao;

import java.math.BigDecimal;

import com.revoult.moneytransfer.model.Account;

public interface AccountDAO {
	
	Integer createAccount(Account account);
	Account getAccount(Integer id);
	void updateAccount(Integer id,Account account);
	void transfer(Integer from, Integer to, BigDecimal amount);
	Account deposite(Integer id,BigDecimal amount);
	Account withdraw(Integer id,BigDecimal amount);

}
