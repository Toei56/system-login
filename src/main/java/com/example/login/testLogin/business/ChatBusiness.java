package com.example.login.testLogin.business;

import com.example.login.testLogin.controller.request.ChatMessage;
import com.example.login.testLogin.controller.request.ChatMessageRequest;
import com.example.login.testLogin.exception.BaseException;
import com.example.login.testLogin.exception.ChatException;
import com.example.login.testLogin.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ChatBusiness {

    private final SimpMessagingTemplate template;

    public ChatBusiness(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(ChatMessageRequest request) throws BaseException {
        Optional<Long> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

       //  TODO: validate message

        final String destination = "chat";

        ChatMessage payload = new ChatMessage();
        payload.setFrom("");
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination, payload);
    }
}
