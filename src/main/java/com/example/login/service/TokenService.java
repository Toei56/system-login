package com.example.login.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login.entityModel.User;

public interface TokenService {

    String tokenize(User user);

    DecodedJWT verify(String token);

}
