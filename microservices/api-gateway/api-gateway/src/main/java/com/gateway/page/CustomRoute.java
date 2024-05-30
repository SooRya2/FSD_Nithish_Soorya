package com.gateway.page;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("pageservice_route")
public class CustomRoute {

	@Bean
	public RouteLocator pageRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route("path_route", r -> r.path("/land/**")  //anything that goes to page
				.uri("http://localhost:8082"))
		
			.build();
	}
}
