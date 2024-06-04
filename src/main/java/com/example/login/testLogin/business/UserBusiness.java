package com.example.login.testLogin.business;

import com.example.login.testLogin.controller.request.UserLoginRequest;
import com.example.login.testLogin.controller.request.UserRegisterRequest;
import com.example.login.testLogin.controller.request.UserRegisterResponse;
import com.example.login.testLogin.entityModel.User;
import com.example.login.testLogin.exception.BaseException;
import com.example.login.testLogin.exception.UserException;
import com.example.login.testLogin.mapper.UserMapper;
import com.example.login.testLogin.service.TokenService;
import com.example.login.testLogin.service.UserService;
import com.example.login.testLogin.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBusiness {

    private final UserMapper userMapper;
    private final UserService userService;
    private final TokenService tokenService;

    public UserBusiness(UserService userService, UserMapper userMapper, TokenService tokenService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public UserRegisterResponse register(UserRegisterRequest registerRequest) throws BaseException {
        User user = userService.createUser(registerRequest);
        return userMapper.USER_REGISTER_RESPONSE(user);
    }

    
    public String login(UserLoginRequest loginRequest) throws BaseException {
        Optional<User> opt = userService.findByEmail(loginRequest.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();
        }
        return tokenService.tokenize(user);
    }

    public String refreshToken() throws BaseException {
        Optional<Long> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        Long userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }
}
