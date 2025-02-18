package com.askthem.user.exception;

public class CustomException extends Exception{
    private String message;
    private int code;
    public CustomException(){}
    public CustomException(String message, int code){
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage(){
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
