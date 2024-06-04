package com.example.login.testLogin.business;

import com.example.login.testLogin.exception.BaseException;
import com.example.login.testLogin.exception.EmailException;
import com.example.login.testLogin.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {

    private final EmailService emailService;

    public EmailBusiness(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendActivateUserMail(String email, String name, String token) throws BaseException {
        String html = null;
        try {
            html = readEmailTemplate();
        } catch (IOException ex) {
            throw EmailException.templateNotFound();
        }

        String finalLink = "http://localhost:3000/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        String subject = "Please activate your account";
        emailService.send(email, subject, html);
    }

    private String readEmailTemplate() throws IOException {
        File file = ResourceUtils.getFile("classpath:email/email-activate-user.html");
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
