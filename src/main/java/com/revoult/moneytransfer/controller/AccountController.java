package com.revoult.moneytransfer.controller;


import javax.ws.rs.Consumes;

import java.math.BigDecimal;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.revoult.moneytransfer.dto.AccountResponse;
import com.revoult.moneytransfer.dto.CreateAccountRequest;
import com.revoult.moneytransfer.model.Account;
import com.revoult.moneytransfer.service.AccountService;

import io.swagger.annotations.Api;

@Path("account")
@Api("account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountController {

	AccountService accountService = new AccountService();

	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public AccountResponse createAccount(CreateAccountRequest req) {
		Integer id = accountService.createAccount(req.getAmount());
		Account acc=accountService.getAccount(id);
		return new AccountResponse(acc.getId(), acc.getBalance());
	}

    @GET
    @Path("/{accountId}")
    public AccountResponse getAccount(@PathParam("accountId") Integer accountId) {
        return accountService.getAccountById(accountId).getAccResponse();
    }
    
    
    @PUT
    @Path("/{accountId}/deposit/{amount}")
    public AccountResponse deposit(@PathParam("accountId") Integer id,@PathParam("amount") BigDecimal amount)  {
    	return accountService.deposite(id, amount).getAccResponse();
    }

    @PUT
    @Path("/{accountId}/withdraw/{amount}")
    public AccountResponse withdraw(@PathParam("accountId") Integer id,@PathParam("amount") BigDecimal amount)  {
    	return accountService.withdraw(id, amount).getAccResponse();
    }

}
