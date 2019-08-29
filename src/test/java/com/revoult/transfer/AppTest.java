package com.revoult.transfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.dao.AccountImpl;
import com.revoult.moneytransfer.exception.SystemException;
import com.revoult.moneytransfer.model.Account;
import com.revoult.moneytransfer.service.AccountService;

public class AppTest {

	
	@Test(expected = SystemException.class)
	public void testAvoidingDeadLockByFailFast() throws InterruptedException {

		AccountImpl accountDAO = new AccountImpl();
	
		Account mainAcc = new Account();
		mainAcc.setBalance(new BigDecimal(2));
		Integer from = accountDAO.createAccount(mainAcc);
		Integer to = accountDAO.createAccount(mainAcc);

		AccountService ser = new AccountService();
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						accountDAO.getAccount(from).getLock().lock();
					} catch (SystemException e) {
						throw e;
					}
				}
			});
			t.start();

		t.join();
		ser.transfer(from, to, new BigDecimal(1));

	}
	@Test
	public void testSameAccountConcurrentTransfers() throws InterruptedException {
		Integer threads=20;
		AtomicInteger successCount = new AtomicInteger(0);
		AtomicInteger failCount = new AtomicInteger(0);
		AccountImpl accountDAO = new AccountImpl();
	
		Account mainAcc = new Account();
		mainAcc.setBalance(new BigDecimal(threads));
		Integer from = accountDAO.createAccount(mainAcc);
		Integer to = accountDAO.createAccount(mainAcc);

		AccountService ser = new AccountService();
		final CountDownLatch latch = new CountDownLatch(threads);
		for (int i = 0; i < threads; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						ser.transfer(from, to, new BigDecimal(1));
						successCount.incrementAndGet();

					} catch (SystemException e) {
						failCount.incrementAndGet();
					} finally {
						latch.countDown();

					}
				}
			}).start();
		}

		latch.await();

		Account fromAcc= accountDAO.getAccount(from);
		Account toAcc= accountDAO.getAccount(to);

		assertThat(mainAcc.getBalance().subtract(new BigDecimal(successCount.get()))).isEqualTo(fromAcc.getBalance());
		assertThat(mainAcc.getBalance().add(new BigDecimal(successCount.get()))).isEqualTo(toAcc.getBalance());


	}

}
