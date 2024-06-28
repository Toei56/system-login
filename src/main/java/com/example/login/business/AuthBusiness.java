package com.example.login.business;

import com.example.login.controller.request.*;
import com.example.login.entityModel.User;
import com.example.login.exception.*;
import com.example.login.mapper.UserMapper;
import com.example.login.service.TokenService;
import com.example.login.service.AuthService;
import com.example.login.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class AuthBusiness {

    private final UserMapper userMapper;
    private final AuthService authService;
    private final TokenService tokenService;
    private final EmailBusiness emailBusiness;

    public AuthBusiness(AuthService authService, UserMapper userMapper, TokenService tokenService, EmailBusiness emailBusiness) {
        this.authService = authService;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;
    }

    public UserRegisterResponse register(UserRegisterRequest registerRequest) {
        String token = SecurityUtil.generateToken();

        User user = authService.createUser(registerRequest, token, nextHour());

        sendEmail(user);

        return userMapper.USER_REGISTER_RESPONSE(user);
    }

    public ActivateResponse activate(ActivateRequest activateRequest) {
        String token = activateRequest.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw BadRequestException.activateNoToken();
        }

        Optional<User> opt = authService.findByToken(token);
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
        authService.updateUser(user);

        ActivateResponse activate = new ActivateResponse();
        activate.setSuccess(true);
        return activate;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw BadRequestException.resendActivationNoToken();
        }

        Optional<User> opt = authService.findByToken(token);
        if (opt.isEmpty()) {
            throw NotFoundException.resendActivationTokenlNotFound();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw ConflictException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextHour());
        user = authService.updateUser(user);
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
        Optional<User> opt = authService.findByEmail(loginRequest.getEmail());
        if (opt.isEmpty()) {
            throw NotFoundException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if (!authService.matchPassword(loginRequest.getPassword(), user.getPassword())) {
            throw UnauthorizedException.loginFailPasswordIncorrect();
        }

        if (!user.isActivated()) {
            throw ForbiddenException.loginFailUserUnactivated();
        }

        UserLoginResponse response = new UserLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }

}
