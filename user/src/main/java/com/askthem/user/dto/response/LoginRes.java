package com.askthem.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
public class LoginRes {
    private String email;
    private String accessToken;
    public LoginRes(){}
    public LoginRes(String email, String accessToken){
        this.email = email;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}