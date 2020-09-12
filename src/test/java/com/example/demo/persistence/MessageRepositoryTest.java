package com.example.demo.persistence;

import com.example.demo.persistence.entity.MessageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void testFindById() throws Exception {
        Optional<MessageEntity> messageO = messageRepository.findById(1L);
        assertTrue(messageO.isPresent());
        MessageEntity message = messageO.get();

        assertEquals(1L, message.getId());
        assertNull(message.getQuestionId());
        assertEquals("John Rambo", message.getAuthor());
        assertEquals("Question 1?", message.getMessage());

        assertEquals(2L, message.getReplies().get(0).getId());
        assertEquals(1L, message.getReplies().get(0).getQuestionId());
        assertEquals("John Snow", message.getReplies().get(0).getAuthor());
        assertEquals("Answer to Question 1", message.getReplies().get(0).getMessage());

        assertEquals(3L, message.getReplies().get(1).getId());
        assertEquals(1L, message.getReplies().get(1).getQuestionId());
        assertEquals("John Travolta", message.getReplies().get(1).getAuthor());
        assertEquals("Answer to Question 1", message.getReplies().get(1).getMessage());
    }

    @Test
    public void testFindByNullQuestionId() throws Exception {
        List<MessageEntity> messages = messageRepository.findByQuestionId(null);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());

        assertEquals(1L, messages.get(0).getId());
        assertNull(messages.get(0).getQuestionId());
        assertEquals("John Rambo", messages.get(0).getAuthor());
        assertEquals("Question 1?", messages.get(0).getMessage());

        assertEquals(2L, messages.get(0).getReplies().get(0).getId());
        assertEquals(1L, messages.get(0).getReplies().get(0).getQuestionId());
        assertEquals("John Snow", messages.get(0).getReplies().get(0).getAuthor());
        assertEquals("Answer to Question 1", messages.get(0).getReplies().get(0).getMessage());

        assertEquals(3L, messages.get(0).getReplies().get(1).getId());
        assertEquals(1L, messages.get(0).getReplies().get(1).getQuestionId());
        assertEquals("John Travolta", messages.get(0).getReplies().get(1).getAuthor());
        assertEquals("Answer to Question 1", messages.get(0).getReplies().get(1).getMessage());

        assertEquals(4L, messages.get(1).getId());
        assertNull(messages.get(1).getQuestionId());
        assertEquals("John Snow", messages.get(1).getAuthor());
        assertEquals("Question 2?", messages.get(1).getMessage());
    }

    @Test
    public void testFindByNotNullQuestionId() throws Exception {
        List<MessageEntity> messages = messageRepository.findByQuestionId(1L);
        assertNotNull(messages);
        assertFalse(messages.isEmpty());

        assertEquals(2L, messages.get(0).getId());
        assertEquals(1L, messages.get(0).getQuestionId());
        assertEquals("John Snow", messages.get(0).getAuthor());
        assertEquals("Answer to Question 1", messages.get(0).getMessage());

        assertEquals(3L, messages.get(1).getId());
        assertEquals(1L, messages.get(1).getQuestionId());
        assertEquals("John Travolta", messages.get(1).getAuthor());
        assertEquals("Answer to Question 1", messages.get(1).getMessage());
    }

    @Test
    public void testFindThread_byQuestion() throws Exception {
        MessageEntity message = messageRepository.findThread(1L);

        assertEquals(1L, message.getId());
        assertNull(message.getQuestionId());
        assertEquals("John Rambo", message.getAuthor());
        assertEquals("Question 1?", message.getMessage());

        assertEquals(2L, message.getReplies().get(0).getId());
        assertEquals(1L, message.getReplies().get(0).getQuestionId());
        assertEquals("John Snow", message.getReplies().get(0).getAuthor());
        assertEquals("Answer to Question 1", message.getReplies().get(0).getMessage());

        assertEquals(3L, message.getReplies().get(1).getId());
        assertEquals(1L, message.getReplies().get(1).getQuestionId());
        assertEquals("John Travolta", message.getReplies().get(1).getAuthor());
        assertEquals("Answer to Question 1", message.getReplies().get(1).getMessage());
    }

    @Test
    public void testFindThread_byReply() throws Exception {
        MessageEntity message = messageRepository.findThread(2L);

        assertEquals(1L, message.getId());
        assertNull(message.getQuestionId());
        assertEquals("John Rambo", message.getAuthor());
        assertEquals("Question 1?", message.getMessage());

        assertEquals(2L, message.getReplies().get(0).getId());
        assertEquals(1L, message.getReplies().get(0).getQuestionId());
        assertEquals("John Snow", message.getReplies().get(0).getAuthor());
        assertEquals("Answer to Question 1", message.getReplies().get(0).getMessage());

        assertEquals(3L, message.getReplies().get(1).getId());
        assertEquals(1L, message.getReplies().get(1).getQuestionId());
        assertEquals("John Travolta", message.getReplies().get(1).getAuthor());
        assertEquals("Answer to Question 1", message.getReplies().get(1).getMessage());
    }

    @Test
    public void testCreateQuestion() throws Exception {
        MessageEntity question = MessageEntity.builder().author("John Do").message("Some question 3").build();
        MessageEntity savedMessage = messageRepository.save(question);
        assertNull(savedMessage.getQuestionId());
        assertEquals(5L, savedMessage.getId());
        assertNull(savedMessage.getQuestionId());
        assertEquals(question.getAuthor(), savedMessage.getAuthor());
        assertEquals(question.getMessage(), savedMessage.getMessage());
    }

    @Test
    public void testCreateReply() throws Exception {
        MessageEntity reply = MessageEntity.builder().questionId(1L).author("John Stevenson").message("Some reply 3").build();
        MessageEntity savedMessage = messageRepository.save(reply);
        assertEquals(6L, savedMessage.getId());
        assertEquals(reply.getQuestionId(), savedMessage.getQuestionId());
        assertEquals(reply.getAuthor(), savedMessage.getAuthor());
        assertEquals(reply.getMessage(), savedMessage.getMessage());
    }
}
