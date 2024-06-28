package com.example.login.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequest {

    @NotEmpty
    @Size(max = 30)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 30)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;

    @Size(min = 10, max = 10)
    private String phone_number = "0909999999";

    private String role = "ROLE_USER";

}
