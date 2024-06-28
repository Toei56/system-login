package com.example.login.service;

import com.example.login.controller.request.UserRegisterRequest;
import com.example.login.entityModel.User;
import com.example.login.exception.ConflictException;
import com.example.login.repository.AuthRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class AuthServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImp(AuthRepository authRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authRepository = authRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(UserRegisterRequest request, String token, Date tokenExpireDate) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw ConflictException.createDuplicate();
        }

        User user = new User()
                .setUsername(request.getUsername())
                .setEmail(request.getEmail())
                .setPassword(bCryptPasswordEncoder.encode(request.getPassword()))
                .setPhone_number(request.getPhone_number())
                .setRole(request.getRole())
                .setToken(token)
                .setTokenExpire(tokenExpireDate);
        return authRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Admin load user from DB : " + id);
        return authRepository.findById(id);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return authRepository.findByToken(token);
    }

    @Override
    public User updateUser(User user) {
        return authRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        authRepository.deleteById(id);
    }

    @Override
    public Boolean matchPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
