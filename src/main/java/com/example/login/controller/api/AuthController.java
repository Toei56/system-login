package com.example.login.controller.api;

import com.example.login.business.AuthBusiness;
import com.example.login.controller.request.*;
import com.example.login.exception.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthBusiness authBusiness;

    public AuthController(AuthBusiness authBusiness) {
        this.authBusiness = authBusiness;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserRegisterResponse register(
            @Valid @RequestBody UserRegisterRequest registerRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw BadRequestException.validateException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }

        return authBusiness.register(registerRequest);
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest loginRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw BadRequestException.validateException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }

        UserLoginResponse login = authBusiness.login(loginRequest);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/activate")
    public ResponseEntity<ActivateResponse> activate(@RequestBody ActivateRequest activateRequest) {
        ActivateResponse response = authBusiness.activate(activateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody ResendActivateEmailRequest request) {
        authBusiness.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<RefreshTonkenResponse> refreshToken() {
        RefreshTonkenResponse response = authBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

}
