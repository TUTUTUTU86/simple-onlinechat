package com.chat.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleMessage {
    private long userId;
    private String body;

    public static SimpleMessage messageToSimpleMessage(Message msg){
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setUserId(msg.getUser().getId());
        simpleMessage.setBody(msg.getBody());
        return simpleMessage;
    }
}
