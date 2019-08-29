package com.revoult.transfer;


import java.math.BigDecimal;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.ClassRule;
import org.junit.Test;
import com.revoult.moneytransfer.constant.ErrorCodes;
import com.revoult.moneytransfer.controller.AccountController;
import com.revoult.moneytransfer.dto.AccountResponse;
import com.revoult.moneytransfer.dto.CreateAccountRequest;
import com.revoult.moneytransfer.dto.Error;
import com.revoult.moneytransfer.exception.mapper.GeneralExceptionMapper;
import com.revoult.moneytransfer.exception.mapper.ServiceExceptionMapper;
import com.revoult.moneytransfer.exception.mapper.WebExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;


import static org.assertj.core.api.Assertions.assertThat;

public class AccountControllerTest {

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new AccountController())
			.setRegisterDefaultExceptionMappers(false).addProvider(new ServiceExceptionMapper())
			.addProvider(new GeneralExceptionMapper()).addProvider(new WebExceptionMapper()).build();

	@Test
	public void testAccountCreating() {

		CreateAccountRequest req = new CreateAccountRequest();
		req.setAmount(new BigDecimal(200));
		AccountResponse res = resources.client().target("/account").request().post(Entity.json(req),
				AccountResponse.class);
		assertThat(req.getAmount()).isEqualTo(res.getBalance());
		assertThat(res.getId()).isNotNull();
	}

	@Test
	public void testAccountCreatingWithInvalidAmount() {
		CreateAccountRequest req = new CreateAccountRequest();
		req.setAmount(new BigDecimal(-200));
		Response res = resources.client().target("/account").request().post(Entity.json(req));
		Error err =res.readEntity(Error.class);
		assertThat(Status.BAD_REQUEST.getStatusCode()).isEqualTo(res.getStatus());
		assertThat(ErrorCodes.INVALID_AMOUNT).isEqualTo(err.getCode());
		assertThat(err.getMessage()).isNotNull();
	}

	@Test
	public void testDeposite() {
		CreateAccountRequest req = new CreateAccountRequest();
		req.setAmount(new BigDecimal(200));
		AccountResponse createRes = resources.client().target("/account").request().post(Entity.json(req),
				AccountResponse.class);
		String path = String.format("/account/%d/deposit/%s", createRes.getId(), createRes.getBalance().toString());
		AccountResponse depositResponse = resources.client().target(path).request(MediaType.APPLICATION_JSON).put(Entity.json(""),AccountResponse.class);
		assertThat(createRes.getBalance().add(createRes.getBalance())).isEqualTo(depositResponse.getBalance() );
		assertThat(depositResponse.getId()).isEqualTo(createRes.getId());
	}
	
	@Test
	public void testWithdarw() {
		CreateAccountRequest req = new CreateAccountRequest();
		req.setAmount(new BigDecimal(200));
		AccountResponse createRes = resources.client().target("/account").request().post(Entity.json(req),
				AccountResponse.class);
		String path = String.format("/account/%d/withdraw/%s", createRes.getId(), createRes.getBalance().toString());
		AccountResponse depositResponse = resources.client().target(path).request(MediaType.APPLICATION_JSON).put(Entity.json(""),AccountResponse.class);
		assertThat(new BigDecimal(0)).isEqualTo(depositResponse.getBalance());
		assertThat(depositResponse.getId()).isEqualTo(createRes.getId());
	}
	
	@Test
	public void testInsufficientBalance() {
		CreateAccountRequest req = new CreateAccountRequest();
		req.setAmount(new BigDecimal(200));
		AccountResponse createRes = resources.client().target("/account").request().post(Entity.json(req),
				AccountResponse.class);
		String path = String.format("/account/%d/withdraw/%s", createRes.getId(), createRes.getBalance().add(createRes.getBalance()).toString());
		Response res = resources.client().target(path).request(MediaType.APPLICATION_JSON).put(Entity.json(""));
		Error errResponse =	res.readEntity(Error.class);
		assertThat(Status.CONFLICT.getStatusCode()).isEqualTo(res.getStatus());
		assertThat(ErrorCodes.INSUFFICIENT_BALANCE).isEqualTo(errResponse.getCode());
		assertThat(errResponse.getMessage()).isNotNull();

	}
}
