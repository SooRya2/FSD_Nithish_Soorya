package com.gateway.login;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
@Component
public class CustomRoute {
	
		@Bean(name="CustomRouteBean")
		public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {
			return builder.routes()
				.route("path_route", r -> r.path("/sso/**")  //anything that goes to login
					.uri("http://localhost:8081"))
				
				.build();
		}
	}


