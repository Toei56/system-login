package com.example.login.controller.api;

import com.example.login.business.UserBusiness;
import com.example.login.controller.request.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getMyUserProfile() {
        UserProfile response = userBusiness.getMyUserProfile();
        return ResponseEntity.ok(response);
    }
}
