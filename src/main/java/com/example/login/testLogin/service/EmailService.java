package com.example.login.testLogin.service;

public interface EmailService {

    void send(String to, String subject, String html);
}
