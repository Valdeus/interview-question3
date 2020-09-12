package com.example.demo.web;

import com.example.demo.domain.Message;
import com.example.demo.domain.Question;
import com.example.demo.domain.Reply;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService mockMessageService;

    /**
     * Tests the happy path for endpoint to create a question
     */
    @Test
    public void testCreateQuestion() throws Exception {

        Message question = Message.builder().author("John Travolta").message("Question 1?").build();
        Message savedQuestion = Message.builder().id(1L).author(question.getAuthor()).message(question.getMessage()).build();

        given(mockMessageService.createQuestion(question)).willReturn(savedQuestion);
        mvc.perform(post("/questions")
                .content(new ObjectMapper().writeValueAsString(question))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedQuestion.getId().intValue())))
                .andExpect(jsonPath("$.author", is(question.getAuthor())))
                .andExpect(jsonPath("$.message", is(question.getMessage())));

        verify(mockMessageService, times(1)).createQuestion(question);
        verifyNoMoreInteractions(mockMessageService);
    }

    /**
     * Tests the happy path for endpoint to create a reply
     */
    @Test
    public void testCreateReply() throws Exception {

        Reply reply = new Reply(Message.builder().author("John Travolta").message("Reply to Question 1.").build());
        Reply savedReply = new Reply(Message.builder().id(1L).author(reply.getAuthor()).message(reply.getMessage()).build());
        savedReply.setQuestionId(1L);

        given(mockMessageService.createReply(any(Reply.class))).willReturn(savedReply);
        mvc.perform(post("/questions/1/reply")
                .content(new ObjectMapper().writeValueAsString(reply))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(savedReply.getId().intValue())))
                .andExpect(jsonPath("$.questionId", is(savedReply.getQuestionId().intValue())))
                .andExpect(jsonPath("$.author", is(savedReply.getAuthor())))
                .andExpect(jsonPath("$.message", is(savedReply.getMessage())));

        verify(mockMessageService, times(1)).createReply(any(Reply.class));
        verifyNoMoreInteractions(mockMessageService);
    }

    /**
     * Tests the happy path for endpoint to vring all questions
     */
    @Test
    public void testGetAllQuestions() throws Exception {

        Reply reply1 = new Reply(new Message(2L, "John Snow", "Some reply"));
        reply1.setQuestionId(1L);
        Reply reply2 = new Reply(new Message(3L, "John Rambo", "Some reply"));
        reply2.setQuestionId(1L);
        Question question1 = new Question(new Message(1L, "Jhon Travolta", "Some question 1?"));
        question1.setReplies(Arrays.asList(reply1, reply2));
        Question question2 = new Question(new Message(4L, "Jhon Doe", "Some question 2?"));
        List<Question> questions = Arrays.asList(question1, question2);

        given(mockMessageService.getAllQuestions()).willReturn(questions);
        mvc.perform(get("/questions")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(question1.getId().intValue())))
                .andExpect(jsonPath("$.[0].author", is(question1.getAuthor())))
                .andExpect(jsonPath("$.[0].message", is(question1.getMessage())))
                .andExpect(jsonPath("$.[0].replies.[0].id", is(question1.getReplies().get(0).getId().intValue())))
                .andExpect(jsonPath("$.[0].replies.[0].author", is(question1.getReplies().get(0).getAuthor())))
                .andExpect(jsonPath("$.[0].replies.[0].message", is(question1.getReplies().get(0).getMessage())))
                .andExpect(jsonPath("$.[0].replies.[1].id", is(question1.getReplies().get(1).getId().intValue())))
                .andExpect(jsonPath("$.[0].replies.[1].author", is(question1.getReplies().get(1).getAuthor())))
                .andExpect(jsonPath("$.[0].replies.[1].message", is(question1.getReplies().get(1).getMessage())))
                .andExpect(jsonPath("$.[1].id", is(question2.getId().intValue())))
                .andExpect(jsonPath("$.[1].author", is(question2.getAuthor())))
                .andExpect(jsonPath("$.[1].message", is(question2.getMessage())));

        verify(mockMessageService, times(1)).getAllQuestions();
        verifyNoMoreInteractions(mockMessageService);
    }

    /**
     * Tests the happy path for endpoint to fecth a thread
     */
    @Test
    public void testGetThread() throws Exception {

        Reply reply1 = new Reply(new Message(2L, "John Snow", "Some reply"));
        reply1.setQuestionId(1L);
        Reply reply2 = new Reply(new Message(3L, "John Rambo", "Some reply"));
        reply2.setQuestionId(1L);
        Question question = new Question(new Message(1L, "Jhon Travolta", "Some question 1?"));
        question.setReplies(Arrays.asList(reply1, reply2));

        given(mockMessageService.getThread(question.getId())).willReturn(question);
        mvc.perform(get("/questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(question.getId().intValue())))
                .andExpect(jsonPath("$.author", is(question.getAuthor())))
                .andExpect(jsonPath("$.message", is(question.getMessage())))
                .andExpect(jsonPath("$.replies.[0].id", is(question.getReplies().get(0).getId().intValue())))
                .andExpect(jsonPath("$.replies.[0].author", is(question.getReplies().get(0).getAuthor())))
                .andExpect(jsonPath("$.replies.[0].message", is(question.getReplies().get(0).getMessage())))
                .andExpect(jsonPath("$.replies.[1].id", is(question.getReplies().get(1).getId().intValue())))
                .andExpect(jsonPath("$.replies.[1].author", is(question.getReplies().get(1).getAuthor())))
                .andExpect(jsonPath("$.replies.[1].message", is(question.getReplies().get(1).getMessage())));

        verify(mockMessageService, times(1)).getThread(question.getId());
        verifyNoMoreInteractions(mockMessageService);
    }
}