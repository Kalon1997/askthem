package com.askthem.user.repository;

import com.askthem.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByEmail(String email);
    List<User> findByCreatedAtBetween(ZonedDateTime fromDate, ZonedDateTime toDate);
}
