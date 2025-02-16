package com.askthem.user.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> throwNewCustomException(Exception ex){ //WebRequest request
        ErrorResponse errorResponse = new ErrorResponse();
        System.out.println("===========GlobalExceptionHandler==========="+ex);
        ex.printStackTrace();  // will remove
        if(ex instanceof CustomException){
            errorResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(((CustomException) ex).getCode()).body(errorResponse);
        }

        if(ex instanceof ClassCastException){ // old v of jwt issue
            errorResponse.setMessage("Empty claims");
            return ResponseEntity.status(403).body(errorResponse);
        }

        if(ex instanceof SignatureException){
            errorResponse.setMessage("Invalid Jwt Signature");
            return ResponseEntity.status(403).body(errorResponse);
        }

        if(ex instanceof MalformedJwtException){
            errorResponse.setMessage("Invalid Jwt Token");
            return ResponseEntity.status(403).body(errorResponse);
        }

        if(ex instanceof ExpiredJwtException){
            errorResponse.setMessage("Jwt Token Expired");
            return ResponseEntity.status(403).body(errorResponse);
        }

        if(ex instanceof BadCredentialsException){
            errorResponse.setMessage("Bad Credentials");
            return ResponseEntity.status(401).body(errorResponse);
        }

        if(ex instanceof HttpClientErrorException.Forbidden){
            errorResponse.setMessage("User Forbidden");
            return ResponseEntity.status(401).body(errorResponse);
        }

        if(ex instanceof NoResourceFoundException){
            errorResponse.setMessage("Resource not found");
            return ResponseEntity.status(404).body(errorResponse);
        }

        if(ex instanceof AccessDeniedException){
            errorResponse.setMessage("Access Denied");
            return ResponseEntity.status(404).body(errorResponse);
        }

        if(ex instanceof NullPointerException){
            errorResponse.setMessage("Null Pointer Exception");
            return ResponseEntity.status(404).body(errorResponse);
        }

        if(ex instanceof UsernameNotFoundException){
            errorResponse.setMessage("User not found");
            return ResponseEntity.status(404).body(errorResponse);
        }


        errorResponse.setMessage("Something went wrong");
        return ResponseEntity.status(500).body(errorResponse);
//        return ResponseEntity.status(((CustomException) ex).getCode()).body(errorResponse);







//        System.out.println(ex+"=======================");
////        if(ex instanceof ClassCastException){
////            errorResponse.setMessage("JWT TOKEN COMPROMISED");
////            return ResponseEntity.status(403).body(errorResponse);
////        }
//        errorResponse.setMessage(ex.getMessage());
//        System.out.println("=======errorResponse=============="+errorResponse);
//        return ResponseEntity.status(403).body(errorResponse);
    }
}
