package com.example.login.service;

import com.example.login.controller.request.UserRegisterRequest;
import com.example.login.entityModel.User;
import com.example.login.exception.ConflictException;
import com.example.login.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(UserRegisterRequest request, String token, Date tokenExpireDate) {
        if (userRepository.existsByEmail(request.getEmail())) {
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
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Load user from DB : " + id);
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Boolean matchPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
