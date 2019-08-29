package com.revoult.moneytransfer.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.revoult.moneytransfer.dto.AccountResponse;
import com.revoult.moneytransfer.dto.TransferRequest;

import com.revoult.moneytransfer.service.AccountService;

import io.swagger.annotations.Api;

@Path("transaction")
@Api("transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {

	AccountService accountService = new AccountService();

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/transfer")
	@POST
	public AccountResponse transferMoney(TransferRequest req) {
		accountService.transfer(req.getFrom(), req.getTo(), req.getAmount());
		return accountService.getAccount(req.getFrom()).getAccResponse();
	}


}
