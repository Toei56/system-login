package com.example.login.business;

import com.example.login.controller.request.*;
import com.example.login.entityModel.User;
import com.example.login.exception.*;
import com.example.login.mapper.UserMapper;
import com.example.login.service.TokenService;
import com.example.login.service.UserService;
import com.example.login.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private final UserMapper userMapper;
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailBusiness emailBusiness;

    public UserBusiness(UserService userService, UserMapper userMapper, TokenService tokenService, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;
    }

    public UserRegisterResponse register(UserRegisterRequest registerRequest) {
        String token = SecurityUtil.generateToken();

        User user = userService.createUser(registerRequest, token, nextHour());

        sendEmail(user);

        return userMapper.USER_REGISTER_RESPONSE(user);
    }

    public ActivateResponse activate(ActivateRequest activateRequest) {
        String token = activateRequest.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw BadRequestException.activateNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw NotFoundException.activateFail();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw ConflictException.activateAlready();
        }

        Date now = new Date();
        Date tokenExpire = user.getTokenExpire();
        if (now.after(tokenExpire)) {
            // TODO: re-email or remove user
            throw GoneException.activateTokenExpire();
        }

        user.setActivated(true);
        userService.update(user);

        ActivateResponse activate = new ActivateResponse();
        activate.setSuccess(true);
        return activate;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) {
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)) {
            throw BadRequestException.resendActivationNoEmail();
        }

        Optional<User> opt = userService.findByEmail(email);
        if (opt.isEmpty()) {
            throw NotFoundException.resendActivationEmailNotFound();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw ConflictException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextHour());
        user = userService.update(user);
        sendEmail(user);
    }

    private Date nextHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }

    private void sendEmail(User user) {
        try {
            emailBusiness.sendActivateUserMail(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("Token: " + user.getToken());
    }

    public UserLoginResponse login(UserLoginRequest loginRequest) {
        Optional<User> opt = userService.findByEmail(loginRequest.getEmail());
        if (opt.isEmpty()) {
            throw NotFoundException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassword(), user.getPassword())) {
            throw UnauthorizedException.loginFailPasswordIncorrect();
        }

        if (!user.isActivated()) {
            throw ForbiddenException.loginFailUserUnactivated();
        }

        UserLoginResponse response = new UserLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
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
