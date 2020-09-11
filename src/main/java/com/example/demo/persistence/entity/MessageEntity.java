package com.example.demo.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "TBL_MESSAGES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "author")
    private String author;

    @Column(name = "message")
    private String message;

    @JoinColumn(name = "question_id")
    @OneToMany(fetch = FetchType.EAGER)
    @Column(insertable=false, updatable=false)
    private List<MessageEntity> replies;

    //Setters and getters

    @Override
    public String toString() {
        return String.format("QuestionEntity [id=%d, questionId=%d, author=%s, message=%s", id, questionId, author, message);
    }
}
