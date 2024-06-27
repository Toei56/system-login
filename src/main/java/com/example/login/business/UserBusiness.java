package com.example.login.business;

import com.example.login.controller.request.UserProfile;
import com.example.login.entityModel.User;
import com.example.login.exception.NotFoundException;
import com.example.login.exception.UnauthorizedException;
import com.example.login.mapper.UserMapper;
import com.example.login.service.AuthService;
import com.example.login.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBusiness {

    private final AuthService authService;
    private final UserMapper userMapper;

    public UserBusiness(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    public UserProfile getMyUserProfile() {
        Optional<Long> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UnauthorizedException.unauthorized();
        }

        Long userId = opt.get();

        Optional<User> optUser = authService.findById(userId);
        if (optUser.isEmpty()) {
            throw NotFoundException.notFound();
        }

        return userMapper.toUserProfile(optUser.get());
    }

}
