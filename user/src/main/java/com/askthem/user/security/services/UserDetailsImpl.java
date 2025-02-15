package com.askthem.user.security.services;

import com.askthem.user.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; //List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public UserDetailsImpl(long id, String username, String emailId, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = username;
        this.email = emailId;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user, String customAuthorityRole ) {

        return new UserDetailsImpl(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(customAuthorityRole)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
