package com.askthem.user.dto.response;

import org.springframework.stereotype.Component;

@Component
public class UserDto {
    private long id;
    private String email;
    private String role;
    private String accessToken;

    public UserDto(){}
    public UserDto(long id, String email, String role, String accessToken){
        this.id = id;
        this.email = email;
        this.accessToken = accessToken;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
