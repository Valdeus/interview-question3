package com.example.demo.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class Question extends Message {
    private List<Message> replies;

    public Question(Message message)
    {
        super(message.getId(), message.getAuthor(), message.getMessage());
    }
}
