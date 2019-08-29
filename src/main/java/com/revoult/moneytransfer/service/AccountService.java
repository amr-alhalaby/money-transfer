package com.revoult.moneytransfer.service;

import java.math.BigDecimal;
import com.revoult.moneytransfer.dao.AccountDAO;
import com.revoult.moneytransfer.dao.AccountImpl;
import com.revoult.moneytransfer.model.Account;

public class AccountService {
	AccountDAO dao = new AccountImpl();

	public Integer createAccount(BigDecimal balance) {
		return dao.createAccount(new Account(balance));
	};
	public Account getAccount(Integer id) {
		return dao.getAccount(id);
	};
	public void transfer(Integer from, Integer to, BigDecimal amount) {
		dao.transfer(from, to, amount);
	};

	public Account deposite(Integer id, BigDecimal amount) {
		return dao.deposite(id, amount);
	};

	public Account withdraw(Integer id, BigDecimal amount) {
		return dao.withdraw(id, amount);
	}
	public Account getAccountById(Integer id) {
		return dao.getAccount(id);
	};

}
