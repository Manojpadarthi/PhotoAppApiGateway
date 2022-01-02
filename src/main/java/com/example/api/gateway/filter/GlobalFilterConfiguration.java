package com.example.api.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {

	final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

	/**
	 * @return
	 */
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	@Bean
	@Order(1)
	public GlobalFilter secondGlobalFilter() {

		

		return (ServerWebExchange exchange, GatewayFilterChain chain) -> {

			logger.info("My Second global pre filter was executed");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				logger.info("My Second global post filter was executed");

			}));

		};

	}

	

	@Bean
	@Order(2)
	public GlobalFilter thirdGlobalFilter() {

		return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
			logger.info("My Third global pre filter was executed");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				logger.info("My third global post filter was executed");
			}));
		};

	}

}
