package com.zemoga.portfolio.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures OpenAPI (Swagger)
 */
@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI().info(new Info()
				.title("Portfolio API")
				.version("1.0")
				.description("Contains various APIs to interact with the Portfolio model"));

	}

}