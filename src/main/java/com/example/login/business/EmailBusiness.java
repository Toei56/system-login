package com.example.login.business;

import com.example.login.common.EmailRequest;
import com.example.login.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class EmailBusiness {
    private final KafkaTemplate<String, EmailRequest> kafkaEmailTemplate;

    public EmailBusiness(KafkaTemplate<String, EmailRequest> kafkaEmailTemplate) {
        this.kafkaEmailTemplate = kafkaEmailTemplate;
    }

    public void sendActivateUserMail(String email, String name, String token) {
        String html;
        try {
            html = readEmailTemplate();
        } catch (IOException ex) {
            throw NotFoundException.templateNotFound();
        }

        log.info("Token = " + token);

        String finalLink = "http://localhost:3000/activate/" + token;
        html = html.replace("${P_NAME}", name);
        html = html.replace("${P_LINK}", finalLink);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(email);
        emailRequest.setSubject("Please activate your account");
        emailRequest.setContent(html);

        CompletableFuture<SendResult<String, EmailRequest>> future = kafkaEmailTemplate.send("activation-email", emailRequest);
        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                handleFailure(throwable);
            } else {
                handleSuccess(result);
            }

        });
    }

    private void handleFailure(Throwable throwable) {
        log.error("Kafka send fail");
        log.error(throwable);
    }

    private void handleSuccess(SendResult<String, EmailRequest> result) {
        log.info("Kafka send success");
        log.info(result);
    }

    private String readEmailTemplate() throws IOException {
        File file = ResourceUtils.getFile("classpath:email/email-activate-user.html");
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
