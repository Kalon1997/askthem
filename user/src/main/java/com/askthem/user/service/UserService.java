package com.askthem.user.service;

import com.askthem.user.model.User;
import com.askthem.user.repository.UserRepository;
import com.askthem.user.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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
}
