package com.example.login.testLogin.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.login.testLogin.entityModel.User;

public interface TokenService {

    String tokenize(User user);

    DecodedJWT verify(String token);

}
