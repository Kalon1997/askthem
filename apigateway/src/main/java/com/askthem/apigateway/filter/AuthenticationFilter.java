package com.askthem.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


// https://medium.com/@kadampritesh46/securing-micro-services-implementing-authentication-and-api-gateway-part-3-4c3930f6bcd4
// https://github.com/renlong567/spring-cloud/blob/master/api-gateway/src/main/java/com/example/apigateway/controller/FallbackController.java
// https://www.codingshuttle.com/spring-boot-hand-book/rest-client/
// https://github.com/oril-software/spring-cloud-api-gateway-jwt/blob/main/gateway-service/pom.xml


@Component
public class AuthenticationFilter  extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    @Autowired
    private RouterValidator routeValidator;

    @Autowired
    private WebClient webClient;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println("==exchange=="+exchange);
            System.out.println("==chain=="+chain);
            if(routeValidator.isSecured.test((ServerHttpRequest) exchange.getRequest())) {
                if(exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Please provide authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
                }
System.out.println("====1===");
                try {
                    System.out.println("====2===");
                    Object reqObj = new Object();
                    Object obj = webClient
                            .post()
                            .uri("http://localhost:3000/USERS/api/auth/validate")
                            .bodyValue(reqObj)
                            .retrieve().bodyToMono(Object.class).block();
System.out.println(obj+"================obj=================");

    //                    template.getForObject("http://FORUM-USERS//auth/validateadmin" + authHeader, String.class);

                } catch (Exception e) {
                    System.out.println("====3==="+e);
                    e.printStackTrace();
                    throw new RuntimeException("You are not authorized dear!!");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }

    public AuthenticationFilter() {
        super(Config.class);
    }





}













