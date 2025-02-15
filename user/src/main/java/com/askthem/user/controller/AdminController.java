package com.askthem.user.controller;

import com.askthem.user.dto.request.UserListRequest;
import com.askthem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> listUsers(@RequestBody UserListRequest userListRequest){
        return ResponseEntity.status(200).body(userRepository.findAll());
    }
}
