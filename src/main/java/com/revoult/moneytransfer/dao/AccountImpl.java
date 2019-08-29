package com.revoult.moneytransfer.dao;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

import com.revoult.moneytransfer.exception.*;
import org.apache.log4j.Logger;
import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.exception.ServiceException;
import com.revoult.moneytransfer.exception.SystemException;
import com.revoult.moneytransfer.model.Account;

public class AccountImpl implements AccountDAO {
	static final Logger logger = Logger.getLogger(AccountImpl.class);

	private static ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();
	ReentrantLock lock = new ReentrantLock();

	public Integer createAccount(Account acc) {
		Integer id = ThreadLocalRandom.current().nextInt(0, 100000);
		accounts.put(id, new Account(id,acc.getBalance()));
		return id;
	};

	public Account getAccount(Integer id) {
		Account acc = accounts.get(id);
		if (acc == null)
			throw new ValidationException(ErrorCodes.INVALID_ACCOUNT_ID,"cant get account: "+id);
		return acc;

	};

	@Override
	public void updateAccount(Integer id, Account account) {
		if (!accounts.containsKey(id))
			throw new ValidationException(ErrorCodes.INVALID_ACCOUNT_ID,"cant get account: "+id);
		accounts.computeIfPresent(id, (k, v) -> account);

	}

	public void transfer(Integer from, Integer to, BigDecimal amount) {
		Account src = getAccount(from);
		Account dst = getAccount(to);

		Boolean l1 = src.getLock().tryLock();
		Boolean l2 = dst.getLock().tryLock();
		try {
			if (l1 && l2) {
				src.withdraw(amount);
				dst.deposite(amount);
			}
		} finally {
			if (l1)
				src.getLock().unlock();
			if (l2)
				dst.getLock().unlock();
		}

		if (!(l1 && l2)) {
			logger.error("Cant get lock l1: " + l1 + "l2: " + l2);
			throw new SystemException(ErrorCodes.TRY_AGAIN,"Cant get lock l1: " + l1 + "l2: " + l2);
		}
		

	}

	@Override
	public Account deposite(Integer id,BigDecimal amount) {
		Account acc=getAccount(id);
		synchronized (acc) {
			acc.deposite(amount);
			updateAccount(id, acc);
		}
		return acc;
	}
	
	@Override
	public Account withdraw(Integer id,BigDecimal amount) {
		Account acc=getAccount(id);
		synchronized (acc) {
			acc.withdraw(amount);
			updateAccount(id, acc);
		}
		return acc;
	}

}
