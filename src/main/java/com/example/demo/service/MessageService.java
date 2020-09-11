package com.example.demo.service;

import com.example.demo.domain.Message;
import com.example.demo.domain.Question;
import com.example.demo.domain.Reply;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.persistence.MessageRepository;
import com.example.demo.persistence.entity.MessageEntity;
import com.example.demo.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Question> getAllQuestions() throws RecordNotFoundException {
        List questions = messageRepository.findByQuestionId(null);
        if (CollectionUtils.isEmpty(questions)) throw new RecordNotFoundException("No questions found");
        return DTOUtil.toQuestions(questions);
    }

    public Question getThread(Long questionId) throws RecordNotFoundException {
        MessageEntity message = messageRepository.findThread(questionId);
        if (message == null) throw new RecordNotFoundException("No questions found");
        return DTOUtil.toQuestion(message);
    }

    public Message createQuestion(Message message) {
        MessageEntity question = MessageEntity.builder()
                .author(message.getAuthor())
                .message(message.getMessage())
                .build();
        MessageEntity saved = messageRepository.save(question);
        return DTOUtil.toMessage(saved);
    }

    public Reply createReply(Reply reply) {
        MessageEntity question = MessageEntity.builder()
                .questionId(reply.getQuestionId())
                .author(reply.getAuthor())
                .message(reply.getMessage())
                .build();
        MessageEntity saved = messageRepository.save(question);
        return DTOUtil.toReply(saved);
    }
}
