package com.example.login.testLogin.service;

import com.example.login.testLogin.controller.request.UserRegisterRequest;
import com.example.login.testLogin.entityModel.User;
import com.example.login.testLogin.exception.BaseException;
import com.example.login.testLogin.exception.UserException;
import com.example.login.testLogin.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(UserRegisterRequest request) throws BaseException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserException.createDuplicate();
        }

        User user = new User()
                .setUsername(request.getUsername())
                .setEmail(request.getEmail())
                .setPassword(bCryptPasswordEncoder.encode(request.getPassword()))
                .setPhone_number(request.getPhone_number())
                .setRole(request.getRole());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Boolean matchPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
