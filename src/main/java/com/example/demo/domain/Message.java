package com.example.demo.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long id;
    private String author;
    private String message;
}
