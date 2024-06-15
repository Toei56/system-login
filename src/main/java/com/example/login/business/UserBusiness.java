package com.example.login.testLogin.business;

import com.example.login.testLogin.controller.request.*;
import com.example.login.testLogin.entityModel.User;
import com.example.login.testLogin.exception.BaseException;
import com.example.login.testLogin.exception.UserException;
import com.example.login.testLogin.mapper.UserMapper;
import com.example.login.testLogin.service.TokenService;
import com.example.login.testLogin.service.UserService;
import com.example.login.testLogin.util.SecurityUtil;
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

    public UserRegisterResponse register(UserRegisterRequest registerRequest) throws BaseException {
        String token = SecurityUtil.generateToken();

        User user = userService.createUser(registerRequest, token, nextHour());

        sendEmail(user);

        return userMapper.USER_REGISTER_RESPONSE(user);
    }

    public ActivateResponse activate(ActivateRequest activateRequest) throws BaseException {
        String token = activateRequest.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.activateFail();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        Date now = new Date();
        Date tokenExpire = user.getTokenExpire();
        if (now.after(tokenExpire)) {
            // TODO: re-email or remove user
            throw UserException.activateTokenExpire();
        }

        user.setActivated(true);
        userService.update(user);

        ActivateResponse activate = new ActivateResponse();
        activate.setSuccess(true);
        return activate;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) throws BaseException {
        String email = request.getEmail();
        if (StringUtil.isNullOrEmpty(email)) {
            throw UserException.resendActivationNoEmail();
        }

        Optional<User> opt = userService.findByEmail(email);
        if (opt.isEmpty()) {
            throw UserException.resendActivationEmailNotFound();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw UserException.activateAlready();
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
        String token = user.getToken();

        try {
            emailBusiness.sendActivateUserMail(user.getEmail(), user.getUsername(), token);
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }

        log.info("Token: " + token);
    }

    public UserLoginResponse login(UserLoginRequest loginRequest) throws BaseException {
        Optional<User> opt = userService.findByEmail(loginRequest.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();
        }

        if (!user.isActivated()) {
            throw UserException.loginFailUserUnactivated();
        }

        UserLoginResponse response = new UserLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
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
