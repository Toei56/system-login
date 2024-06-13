package com.example.login.testLogin.controller.api;

import com.example.login.testLogin.business.UserBusiness;
import com.example.login.testLogin.controller.request.*;
import com.example.login.testLogin.exception.BaseException;
import com.example.login.testLogin.exception.ValidateException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserRegisterResponse register(
            @Valid @RequestBody UserRegisterRequest registerRequest,
            BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw new ValidateException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }

        return userBusiness.register(registerRequest);
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest loginRequest,
            BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw new ValidateException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }

        UserLoginResponse login = userBusiness.login(loginRequest);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/activate")
    public ResponseEntity<ActivateResponse> activate(@RequestBody ActivateRequest activateRequest) throws BaseException {
        ActivateResponse response = userBusiness.activate(activateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody ResendActivateEmailRequest request) throws BaseException {
        userBusiness.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = userBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }
}
