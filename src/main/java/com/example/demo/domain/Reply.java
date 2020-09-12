package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends Message {
    private Long questionId;

    public Reply(Message message)
    {
        super(message.getId(), message.getAuthor(), message.getMessage());
    }
}
