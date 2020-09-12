package com.example.demo.service;

import com.example.demo.domain.Message;
import com.example.demo.domain.Question;
import com.example.demo.domain.Reply;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.persistence.MessageRepository;
import com.example.demo.persistence.entity.MessageEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class MessageServiceTest {


    private MessageService messageService;

    @Mock
    private MessageRepository mockMessageRepository;

    private static final Long ID1 = 1L;

    @BeforeEach
    private void init() {
        MockitoAnnotations.initMocks(this);
        messageService = new MessageService(mockMessageRepository);
    }

    @Test
    public void testGetAllQuestions_happyPath() throws Exception {

        MessageEntity reply1 = new MessageEntity(2L, ID1, "John Snow", "Some reply", Collections.emptyList());
        MessageEntity reply2 = new MessageEntity(3L, ID1, "John Rambo", "Some reply", Collections.emptyList());
        MessageEntity question1 = new MessageEntity(ID1, null, "Jhon Travolta", "Some question 1?", Arrays.asList(reply1, reply2));
        MessageEntity question2 = new MessageEntity(4L, null, "Jhon Doe", "Some question 2?", Collections.emptyList());
        List<MessageEntity> questions = Arrays.asList(question1, question2);

        given(mockMessageRepository.findByQuestionId(null)).willReturn(questions);

        List<Question> results = messageService.getAllQuestions();

        assertEquals(questions.get(0).getId(), results.get(0).getId());
        assertEquals(questions.get(0).getAuthor(), results.get(0).getAuthor());
        assertEquals(questions.get(0).getMessage(), results.get(0).getMessage());
        assertEquals(questions.get(0).getReplies().get(0).getId(), results.get(0).getReplies().get(0).getId());
        assertEquals(questions.get(0).getReplies().get(0).getAuthor(), results.get(0).getReplies().get(0).getAuthor());
        assertEquals(questions.get(0).getReplies().get(0).getMessage(), results.get(0).getReplies().get(0).getMessage());
        assertEquals(questions.get(0).getReplies().get(1).getId(), results.get(0).getReplies().get(1).getId());
        assertEquals(questions.get(0).getReplies().get(1).getAuthor(), results.get(0).getReplies().get(1).getAuthor());
        assertEquals(questions.get(0).getReplies().get(1).getMessage(), results.get(0).getReplies().get(1).getMessage());
        assertEquals(questions.get(1).getId(), results.get(1).getId());
        assertEquals(questions.get(1).getAuthor(), results.get(1).getAuthor());
        assertEquals(questions.get(1).getMessage(), results.get(1).getMessage());

        verify(mockMessageRepository, times(1)).findByQuestionId(null);
        verifyNoMoreInteractions(mockMessageRepository);
    }

    @Test
    public void testGetAllQuestions_throwsException_mustPropagate() {

        RecordNotFoundException ex = new RecordNotFoundException("Record not found");
        given(mockMessageRepository.findByQuestionId(null)).willThrow(ex);

        Assertions.assertThrows(RecordNotFoundException.class, () -> messageService.getAllQuestions());

        verify(mockMessageRepository, times(1)).findByQuestionId(null);
        verifyNoMoreInteractions(mockMessageRepository);
    }

    @Test
    public void testGetThread_happyPath() throws Exception {

        MessageEntity reply1 = new MessageEntity(2L, ID1, "John Snow", "Some reply", Collections.emptyList());
        MessageEntity reply2 = new MessageEntity(3L, ID1, "John Rambo", "Some reply", Collections.emptyList());
        MessageEntity question = new MessageEntity(ID1, null, "Jhon Travolta", "Some question 1?", Arrays.asList(reply1, reply2));

        given(mockMessageRepository.findThread(ID1)).willReturn(question);

        Question result = messageService.getThread(ID1);

        assertEquals(question.getId(), result.getId());
        assertEquals(question.getAuthor(), result.getAuthor());
        assertEquals(question.getMessage(), result.getMessage());
        assertEquals(question.getReplies().get(0).getId(), result.getReplies().get(0).getId());
        assertEquals(question.getReplies().get(0).getAuthor(), result.getReplies().get(0).getAuthor());
        assertEquals(question.getReplies().get(0).getMessage(), result.getReplies().get(0).getMessage());
        assertEquals(question.getReplies().get(1).getId(), result.getReplies().get(1).getId());
        assertEquals(question.getReplies().get(1).getAuthor(), result.getReplies().get(1).getAuthor());
        assertEquals(question.getReplies().get(1).getMessage(), result.getReplies().get(1).getMessage());

        verify(mockMessageRepository, times(1)).findThread(ID1);
        verifyNoMoreInteractions(mockMessageRepository);
    }

    @Test
    public void testGetThread_throwsException_mustPropagate() {

        RecordNotFoundException ex = new RecordNotFoundException("Record not found");
        given(mockMessageRepository.findThread(ID1)).willThrow(ex);

        Assertions.assertThrows(RecordNotFoundException.class, () -> messageService.getThread(ID1));

        verify(mockMessageRepository, times(1)).findThread(ID1);
        verifyNoMoreInteractions(mockMessageRepository);
    }

    @Test
    public void testCreateQuestion_happyPath() throws Exception {

        Message question = Message.builder().author("John Snow").message("Some question 1?").build();
        MessageEntity questionSaved = MessageEntity.builder().id(ID1).author("John Snow").message("Some question 1?").build();

        given(mockMessageRepository.save(any(MessageEntity.class))).willReturn(questionSaved);

        Message result = messageService.createQuestion(question);

        assertEquals(ID1, result.getId());
        assertEquals(question.getAuthor(), result.getAuthor());
        assertEquals(question.getMessage(), result.getMessage());

        verify(mockMessageRepository, times(1)).save(any(MessageEntity.class));
        verifyNoMoreInteractions(mockMessageRepository);
    }

    @Test
    public void createReply_happyPath() throws Exception {
        final Long REPLY_ID = 2L;
        Reply reply = new Reply(Message.builder().author("John Snow").message("Some question 1?").build());
        reply.setQuestionId(ID1);
        MessageEntity replySaved = MessageEntity.builder().id(REPLY_ID).questionId(ID1).author("John Snow").message("Some question 1?").build();

        given(mockMessageRepository.save(any(MessageEntity.class))).willReturn(replySaved);

        Reply result = messageService.createReply(reply);

        assertEquals(REPLY_ID, result.getId());
        assertEquals(ID1, result.getQuestionId());
        assertEquals(reply.getAuthor(), result.getAuthor());
        assertEquals(reply.getMessage(), result.getMessage());

        verify(mockMessageRepository, times(1)).save(any(MessageEntity.class));
        verifyNoMoreInteractions(mockMessageRepository);
    }


}
