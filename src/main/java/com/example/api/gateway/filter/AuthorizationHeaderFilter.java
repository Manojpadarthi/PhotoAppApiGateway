package com.example.api.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>
{
	@Autowired
	Environment env;
	
	public AuthorizationHeaderFilter()
	{
		super(Config.class);
	}
	
	
	public static class Config
	{
		//put some configuration properties here
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		
		return (exchange,chain)->
		{
			
			ServerHttpRequest request=exchange.getRequest();
			
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
			{
				return onError(exchange,"No authorization header",HttpStatus.UNAUTHORIZED);
			}
			
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			
			String jwt = authorizationHeader.replace("Bearer","");
			if(!isJwtvalid(jwt) ) 
			{
				return onError(exchange,"JWt token invalid",HttpStatus.UNAUTHORIZED);
			}
			
			return chain.filter(exchange);
			
		};
	}


	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus httpStatus) 
	{
		ServerHttpResponse response=exchange.getResponse();
		
		response.setStatusCode(httpStatus);
		
		return response.setComplete();
	}
	
	private boolean isJwtvalid(String jwt) 
	{
		String subject = null;
		try {
		 subject=Jwts.parser().setSigningKey(env.getProperty("token.secret"))
		.parseClaimsJws(jwt).getBody().getSubject();
		}
		catch(Exception e) 
		{
			return false;
		}
		
		
		if(subject.isEmpty() || subject == null) {
			return false;
		}
		
		return true;
	}


	
}
