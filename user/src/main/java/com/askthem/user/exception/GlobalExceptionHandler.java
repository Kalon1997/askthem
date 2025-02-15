package com.askthem.user.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> throwNewCustomException(Exception ex){ //WebRequest request
        ErrorResponse errorResponse = new ErrorResponse();
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
