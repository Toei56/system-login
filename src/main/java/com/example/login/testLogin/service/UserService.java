package com.example.login.testLogin.service;

import com.example.login.testLogin.controller.request.UserRegisterRequest;
import com.example.login.testLogin.entityModel.User;
import com.example.login.testLogin.exception.BaseException;

import java.util.Optional;

public interface UserService {

    User createUser(UserRegisterRequest registerRequest) throws BaseException;

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Boolean matchPassword(String rawPassword, String encodedPassword);
}
