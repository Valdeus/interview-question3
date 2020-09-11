package com.example.demo.util;

import com.example.demo.domain.Message;
import com.example.demo.domain.Question;
import com.example.demo.domain.Reply;
import com.example.demo.persistence.entity.MessageEntity;

import java.util.ArrayList;
import java.util.List;

public class DTOUtil {

    public static Question toQuestion(MessageEntity entity) {
        Question question = new Question(toMessage(entity));
        question.setReplies(toMessages(entity.getReplies()));
        return question;
    }

    public static Reply toReply(MessageEntity entity) {
        Reply reply = new Reply(toMessage(entity));
        reply.setQuestionId(entity.getQuestionId());
        return reply;
    }

    public static Message toMessage(MessageEntity entity) {
        Message message = new Message();
        message.setId(entity.getId());
        message.setAuthor(entity.getAuthor());
        message.setMessage(entity.getMessage());
        return message;
    }

    public static List<Question> toQuestions(List<MessageEntity> entities) {
        List<Question> questions = new ArrayList<>();
        for (MessageEntity messageEntity : entities) {
            questions.add(toQuestion(messageEntity));
        }
        return questions;
    }

    public static List<Message> toMessages(List<MessageEntity> entities) {
        List<Message> messages = new ArrayList<>();
        for (MessageEntity messageEntity : entities) {
            messages.add(toMessage(messageEntity));
        }
        return (List<Message>) messages;
    }
}
