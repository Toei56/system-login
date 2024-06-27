package com.example.login.service;

import com.example.login.controller.request.UserRegisterRequest;
import com.example.login.entityModel.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;
import java.util.Optional;

public interface AuthService {

    User createUser(UserRegisterRequest registerRequest, String token, Date tokenExpireDate);

    Optional<User> findByEmail(String email);

    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    Optional<User> findById(Long id);

    Optional<User> findByToken(String token);

    @CachePut(value = "user", key = "#id")
    User updateUser(User user);

    @CacheEvict(value = "user", key = "#id") //ถ้าลบทั้งหมด เปลี่ยน key เป็น allEntries = true
    void deleteUser(Long id);

    Boolean matchPassword(String rawPassword, String encodedPassword);
}
