package com.askthem.apigateway.exception;

import com.askthem.apigateway.filter.AuthFilter;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(
            ErrorAttributes g, ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer) {
        super(g, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

//    /**
//     * Create a new {@code AbstractErrorWebExceptionHandler}.
//     *
//     * @param errorAttributes    the error attributes
//     * @param resources          the resources configuration properties
//     * @param applicationContext the application context
//     * @since 2.4.0
//     */
//    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext) {
//        super(errorAttributes, resources, applicationContext);
//    }

    @ExceptionHandler({WebClientResponseException.class, HttpServerErrorException.class})
    public ResponseEntity<?> throwNewCustomException(RuntimeException ex) { //WebRequest request

//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        return ResponseEntity.status(401).body("User NF");

        ErrorResponse errorResponse = new ErrorResponse();
        System.out.println("===========GlobalExceptionHandler===========" + ex);
//        ex.printStackTrace();  // will remove
//        if (ex instanceof CustomException) {
//            errorResponse.setMessage(ex.getMessage());
//            return ResponseEntity.status(((CustomException) ex).getCode()).body(errorResponse);
//        }
        if (ex instanceof WebClientResponseException) {
            System.out.println("=======WebClientResponseException======");
            errorResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(400).body(errorResponse);
        }

        if(ex instanceof HttpServerErrorException){
            System.out.println("=======HttpServerErrorException======");
            errorResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(400).body(errorResponse);
        }


        errorResponse.setMessage("Something went wrong");
        return ResponseEntity.status(500).body(errorResponse);
//        return ResponseEntity.status(((CustomException) ex).getCode()).body(errorResponse);

    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        // @formatter:off
        ErrorAttributeOptions errorAttributeOptions = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.values());
        Map<String, Object> errorAttributes = getErrorAttributes(request, errorAttributeOptions);

        Throwable error = getError(request);
        final String message;

        if (error instanceof WebClientResponseException exception) {
            message = exception.getMessage();
        } else {
            message = (String) errorAttributes.get("message");
        }

        Map<String, Object> responseBody = Map.of("message", message);

        int statusCode = 400; //Integer.parseInt(Objects.toString(errorAttributes.get("status")));
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(responseBody));
        // @formatter:on
    }
}