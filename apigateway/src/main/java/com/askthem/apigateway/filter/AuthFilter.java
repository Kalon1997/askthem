package com.askthem.apigateway.filter;

import com.askthem.apigateway.dto.UserDto;
import com.askthem.apigateway.exception.CustomException;
import com.askthem.apigateway.exception.ErrorResponse;
import com.askthem.apigateway.exception.MyCustomExceptionClientIssue;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Controller
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    public static class Config{}
    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder){
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config){
            return (exchange, chain) -> {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization information");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                String[] parts = authHeader.split(" ");

                if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                    throw new RuntimeException("Incorrect authorization structure");
                }
                System.out.println("=====token======="+parts[1]);
            try{
                return webClientBuilder.build()
                        .post()
                        .uri("http://localhost:3000/USER/api/auth/validateToken?token=" + parts[1])
                        .retrieve()

//                        .onStatus(HttpStatusCode::is4xxClientError, response -> {
//                            return Mono.error(new WebClientResponseException(400, "User not found issue",null, null, null));
//                                }
//                        )
//                        .onStatus(HttpStatusCode::is4xxClientError, response -> {
//                            System.out.println("================================4=======");
//                            return response.bodyToMono(String.class)
//                                    .flatMap((error -> {
//                                        System.out.println("======inside error block=========");
//
//                                        return Mono.error(new HttpServerErrorException(HttpStatus.NOT_FOUND, "User not found"));
////                                        return Mono.error(new WebClientResponseException(400, "User NF", null, null, null));
//                                    }));
//                        })
                        .onStatus(HttpStatusCode::is4xxClientError, response -> {
                            System.out.println("================================4=======");
                            return response.bodyToMono(String.class).handle((error, handler) -> {
                                System.out.println("======inside error block========="+error);
                                handler.error(new WebClientResponseException(400, "USER NF", null, null, null));
                            });

                        })


//                        .onStatus(HttpStatusCode::is5xxServerError, response ->{
//                            response.bodyToMono(ErrorResponse.class)
//                                    .flatMap(error -> Mono.error(new WebClientResponseException(500, "User not found issue",null, null, null)))
//                                })

//                        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
//                            System.out.println("=====================onStatus block===400==");
//
//                            return Mono.error(new CustomException("400 error",400));
//                        })
//                        .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
//                            System.out.println("=====================onStatus block===500==");
//                            return Mono.error(new CustomException("500 error", 500));
//                        })
                        .bodyToMono(UserDto.class)

//                        .onErrorMap(Throwable.class, throwable -> new Exception("plain exception"))
//                        .onErrorMap(WebClientResponseException.class, ex -> {
////                            ErrorResponse errorResponse = new ErrorResponse("onErrorMap block in gateway");
////                            return ResponseEntity.status(401).body(errorResponse);
//                            throw new CustomException("onErrorMap block in gateway", 401);
//                        })
//                        .onErrorResume(Mono::error)
//                        .retryWhen(Retry.backoff(3, Duration.of(2, ChronoUnit.SECONDS))
//                                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
//                                        new CustomException("retrySignal.failure()", 401)))
//                    .block();
//                    .doOnError((throwable -> {
//                        Mono.error(new CustomException("Error error", 401));
//                    }))
//                        .onErrorResume(e -> Mono.just(new RuntimeException("My Excep"))

                        .map(userDto -> {
                            exchange.getRequest()
                                    .mutate()
                                    .header("X-auth-user-id", String.valueOf(userDto.getId()));
                            return exchange;
                        })
//                        .filter(errorHandler())
//                        .subscribe(System.out::println, error -> System.out.println("Error: " + error.getMessage()))
                        .flatMap(chain::filter);

//                        .onErrorResume(e -> {
//                            System.out.println("on err resume");
//                            return Mono.error(new RuntimeException("on err resume"));
////                            return Mono.just("Fall back");
//                        });
//                        .subscribe(System.out::println);

            } catch (RuntimeException e) {
                throw new WebClientResponseException(400, "User NF", null, null, null);
            }

            };
        }




//    public static Predicate<? super ServerWebExchange> errorHandler() {
//        return (Predicate<? super ServerWebExchange>) ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//            if (clientResponse.statusCode().is5xxServerError()) {
//                return clientResponse.bodyToMono(String.class)
//                        .flatMap(errorBody -> Mono.error(new CustomException("Custom msg 5xx ", 501)));
//            } else if (clientResponse.statusCode().is4xxClientError()) {
//                return clientResponse.bodyToMono(String.class)
//                        .flatMap(errorBody -> Mono.error(new CustomException("Custom msg 4xx ", 401)));
//            } else {
//                return Mono.just(clientResponse);
//            }
//        });
//    }





    }






