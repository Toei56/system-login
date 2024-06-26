package com.example.login.controller.api;

import com.example.login.business.UserBusiness;
import com.example.login.controller.request.*;
import com.example.login.exception.BadRequestException;
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
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> {
                throw BadRequestException.validateException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
            });
        }

        return userBusiness.register(registerRequest);
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

        UserLoginResponse login = userBusiness.login(loginRequest);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/activate")
    public ResponseEntity<ActivateResponse> activate(@RequestBody ActivateRequest activateRequest) {
        ActivateResponse response = userBusiness.activate(activateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody ResendActivateEmailRequest request) {
        userBusiness.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<RefreshTonkenResponse> refreshToken() {
        RefreshTonkenResponse response = userBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getMyUserProfile() {
        UserProfile response = userBusiness.getMyUserProfile();
        return ResponseEntity.ok(response);
    }
}
