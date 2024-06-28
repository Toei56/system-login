package com.example.login.service;

import com.example.login.entityModel.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface UserService {

    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    Optional<User> findById(Long id);
}
