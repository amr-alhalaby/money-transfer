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
import com.revoult.moneytransfer.controller.TransactionController;
import com.revoult.moneytransfer.dto.AccountResponse;
import com.revoult.moneytransfer.dto.CreateAccountRequest;
import com.revoult.moneytransfer.dto.TransferRequest;
import com.revoult.moneytransfer.exception.mapper.GeneralExceptionMapper;
import com.revoult.moneytransfer.exception.mapper.ServiceExceptionMapper;
import com.revoult.moneytransfer.exception.mapper.WebExceptionMapper;
import io.dropwizard.testing.junit.ResourceTestRule;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionControllerTest {

	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new AccountController()).addResource(new TransactionController())
			.setRegisterDefaultExceptionMappers(false).addProvider(new ServiceExceptionMapper())
			.addProvider(new GeneralExceptionMapper()).addProvider(new WebExceptionMapper()).build();

	@Test
	public void testRansfer() {
		CreateAccountRequest req = new CreateAccountRequest();
		req.setAmount(new BigDecimal(200));
		AccountResponse fromAcc = resources.client().target("/account").request().post(Entity.json(req),
				AccountResponse.class);
		AccountResponse toAcc = resources.client().target("/account").request().post(Entity.json(req),
				AccountResponse.class);

		BigDecimal amount = new BigDecimal(100);
		TransferRequest transReq = new TransferRequest();
		transReq.setFrom(fromAcc.getId());
		transReq.setTo(toAcc.getId());
		transReq.setAmount(amount);
		AccountResponse res = resources.client().target("/transaction/transfer").request(MediaType.APPLICATION_JSON)
				.post(Entity.json(transReq), AccountResponse.class);
		assertThat(fromAcc.getBalance().subtract(amount)).isEqualTo(res.getBalance());
		assertThat(fromAcc.getId()).isEqualTo(res.getId());
		
		AccountResponse newToAcc = resources.client().target("/account/"+toAcc.getId()).request().get(AccountResponse.class);
		assertThat(toAcc.getBalance().add(amount)).isEqualTo(newToAcc.getBalance());
		assertThat(toAcc.getId() ).isEqualTo(newToAcc.getId());
	}

}
