package com.askthem.apigateway.exception;

import org.springframework.stereotype.Component;

public class MyCustomExceptionClientIssue extends RuntimeException{

    public MyCustomExceptionClientIssue(String s) {
        super(s);
    }

//    @Override
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    private String message;
//    public MyCustomExceptionClientIssue(String s){
//        super(s);
//        this.message = s;
//    }
//
//    public MyCustomExceptionClientIssue(){
//
//    }
}
