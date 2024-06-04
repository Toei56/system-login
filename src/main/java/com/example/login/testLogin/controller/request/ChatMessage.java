package com.example.login.testLogin.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChatMessage {

    public ChatMessage() {
        created = new Date();
    }

    private String from;

    private String message;

    private Date created;


}
