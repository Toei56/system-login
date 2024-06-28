package com.example.login.business;

import com.example.login.controller.request.RefreshTonkenResponse;
import com.example.login.controller.request.UserProfile;
import com.example.login.entityModel.User;
import com.example.login.exception.NotFoundException;
import com.example.login.exception.UnauthorizedException;
import com.example.login.mapper.UserMapper;
import com.example.login.service.TokenService;
import com.example.login.service.UserService;
import com.example.login.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private final UserMapper userMapper;
    private final UserService userService;
    private final TokenService tokenService;

    public UserBusiness( UserMapper userMapper, UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public UserProfile getMyUserProfile() {
        Optional<Long> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UnauthorizedException.unauthorized();
        }

        Long userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw NotFoundException.notFound();
        }

        User user = optUser.get();
        return userMapper.toUserProfile(user);
    }

    public RefreshTonkenResponse refreshToken() {
        Optional<Long> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UnauthorizedException.unauthorized();
        }

        Long userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw NotFoundException.notFound();
        }

        User user = optUser.get();
        RefreshTonkenResponse token = new RefreshTonkenResponse();
        token.setToken(tokenService.tokenize(user));
        return token;
    }

}
