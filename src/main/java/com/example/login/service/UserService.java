package com.example.login.service;

import com.example.login.controller.request.UserRegisterRequest;
import com.example.login.entityModel.User;

import java.util.Date;
import java.util.Optional;

public interface UserService {

    User createUser(UserRegisterRequest registerRequest, String token, Date tokenExpireDate);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByToken(String token);

    User update(User user);

    Boolean matchPassword(String rawPassword, String encodedPassword);
}
