package com.revoult.moneytransfer;


import com.revoult.moneytransfer.configuration.AppConfiguration;
import com.revoult.moneytransfer.controller.AccountController;
import com.revoult.moneytransfer.controller.TransactionController;
import com.revoult.moneytransfer.exception.mapper.GeneralExceptionMapper;
import com.revoult.moneytransfer.exception.mapper.ServiceExceptionMapper;
import com.revoult.moneytransfer.exception.mapper.WebExceptionMapper;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


public class App extends Application<AppConfiguration> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "MoneyTransfer";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<AppConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

   
	@Override
	public void run(AppConfiguration c, Environment e) throws Exception {
		e.jersey().register(new WebExceptionMapper());
		e.jersey().register(new GeneralExceptionMapper());
		e.jersey().register(new ServiceExceptionMapper());
		e.jersey().register(new AccountController());
		 e.jersey().register(new TransactionController());

	}

}