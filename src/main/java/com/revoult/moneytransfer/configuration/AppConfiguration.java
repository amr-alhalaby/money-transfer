package com.revoult.moneytransfer.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class AppConfiguration extends Configuration {
	@JsonProperty("swagger")
	public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
