package com.example.login.testLogin.controller.api;

import com.example.login.testLogin.business.UserBusiness;
import com.example.login.testLogin.controller.request.UserLoginRequest;
import com.example.login.testLogin.controller.request.UserRegisterRequest;
import com.example.login.testLogin.controller.request.UserRegisterResponse;
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
    public ResponseEntity<String> login(
            @Valid @RequestBody UserLoginRequest loginRequest,
            BindingResult bindingResult) throws BaseException {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw new ValidateException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }

        String login = userBusiness.login(loginRequest);
        return ResponseEntity.ok(login);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = userBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }
}
