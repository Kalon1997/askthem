package com.askthem.user.controller;

import com.askthem.user.dto.request.UserLogin;
import com.askthem.user.dto.response.LoginRes;
import com.askthem.user.dto.request.UserRegister;
import com.askthem.user.model.User;
import com.askthem.user.repository.UserRepository;
import com.askthem.user.security.jwt.JwtUtils;
import com.askthem.user.security.services.UserDetailsImpl;
import com.askthem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegister userRegister) throws Exception{
        if(userRepository.findByEmail(userRegister.getEmail()) != null){
            throw new Exception("User already exists");
        }
        User user = new User();
        user.setEmail(userRegister.getEmail());
        user.setPassword(encoder.encode(userRegister.getPassword()));
        user.setRole("USER");
        System.out.println(user);
        // will set userName later
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) throws Exception{
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication,"USER" );
        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok().body(new LoginRes(userDetails.getEmail(), jwt));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile() throws Exception{
        User user = userService.getCurrentUser();
        System.out.println("====user==="+user);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().body("hello");
    }
}
