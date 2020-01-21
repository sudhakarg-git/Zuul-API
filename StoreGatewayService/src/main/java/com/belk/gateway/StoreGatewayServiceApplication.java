package com.belk.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StoreGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreGatewayServiceApplication.class, args);
	}
	
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		//@formatter:off
		return builder.routes()
				.route(r -> r.path("/depts/departments")
						.uri("http://localhost:8091/departments")
				)
				.build();
		////@formatter:on
	}

}
