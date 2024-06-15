package com.example.login.testLogin.service;

import com.example.login.testLogin.controller.request.UserRegisterRequest;
import com.example.login.testLogin.entityModel.User;
import com.example.login.testLogin.exception.BaseException;

import java.util.Date;
import java.util.Optional;

public interface UserService {

    User createUser(UserRegisterRequest registerRequest, String token, Date tokenExpireDate) throws BaseException;

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByToken(String token);

    User update(User user);

    Boolean matchPassword(String rawPassword, String encodedPassword);
}
