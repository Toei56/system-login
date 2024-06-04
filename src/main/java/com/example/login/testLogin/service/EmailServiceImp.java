package com.example.login.testLogin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.email.from}")
    private String from;

    public EmailServiceImp(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String html) {
        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true); //ถ้าจะส่งเป็น html ต้องใส่ true ด้วย
        };
        mailSender.send(message);
    }
}
