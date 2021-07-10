package com.example.api.gateway.filter;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyPreFilter implements GlobalFilter,Ordered
{
	
	final Logger logger = LoggerFactory.getLogger(MyPreFilter.class);

	
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		
		logger.info("My first pre-filter is executed...");
		
		HttpHeaders headers = exchange.getRequest().getHeaders();
	 
		Set<String> headerNames=headers.keySet();
	 
		headerNames.forEach(headeName->{
			
			String headerValue = headers.getFirst(headeName);
			logger.info(headeName+"  "+headerValue);
		
		});
		
		return chain.filter(exchange);
	}


	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
