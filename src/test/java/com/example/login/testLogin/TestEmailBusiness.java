package com.example.login.testLogin;

import com.example.login.testLogin.business.EmailBusiness;
import com.example.login.testLogin.exception.BaseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEmailBusiness {

    @Autowired
    private EmailBusiness emailBusiness;

    @Order(1)
    @Test
    void testSendActivateEmail() throws BaseException {
        emailBusiness.sendActivateUserMail(
                TestDataEmail.email,
                TestDataEmail.name,
                TestDataEmail.token);
    }

    interface TestDataEmail {
        String email = "spxth5735@gmail.com";
        String name = "SPXTH5735";
        String token = "token.jwt.1234";
    }
}
