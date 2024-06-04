package com.example.login.testLogin.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {

    @NotEmpty
    @Size(min = 6, max = 30)
    private String email;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;
}
