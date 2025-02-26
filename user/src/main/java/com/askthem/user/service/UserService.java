package com.askthem.user.service;

import com.askthem.user.dto.response.UserDto;
import com.askthem.user.exception.CustomException;
import com.askthem.user.model.User;
import com.askthem.user.repository.UserRepository;
import com.askthem.user.security.jwt.JwtUtils;
import com.askthem.user.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public long getCurrentUserId() {
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

    public User getCurrentUser() {
        long id = this.getCurrentUserId();
        System.out.println("====getCurrentUser==="+id);
        return userRepository.findById(id).get();
//        return userRepository.getOne(id);  // SerializationFeature.FAIL_ON_EMPTY_BEANS ex
    }

    public UserDto validateToken(String token) throws CustomException {
        String userEmail = jwtUtils.getEmailFromJwtToken(token);
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(userEmail));
        if (userOptional == null  || userOptional.isEmpty()) {
            throw new CustomException("User not found - validateToken method", 404);
        }
        User user = userOptional.get();
        UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getRole(), token);
        return userDto;
    }
}
