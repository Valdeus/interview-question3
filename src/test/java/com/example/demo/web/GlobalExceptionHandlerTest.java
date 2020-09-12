package com.example.demo.web;

import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({MessageController.class, GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService mockMessageService;

    /**
     * Tests the path for endpoint to get all questions and checks the propagation of an exception
     */
    @Test
    public void testGetAllQuestions_propagatesRecordNotFoundException() throws Exception {

        RecordNotFoundException recordNotFoundException = new RecordNotFoundException("No record found");

        given(mockMessageService.getAllQuestions()).willThrow(recordNotFoundException);

        mvc.perform(get("/questions")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.reason", is(recordNotFoundException.getMessage())));


        verify(mockMessageService, times(1)).getAllQuestions();
        verifyNoMoreInteractions(mockMessageService);
    }

    /**
     * Tests the path for endpoint to get a thread and checks the propagation of an exception
     */
    @Test
    public void testGetThread_propagatesRecordNotFoundException() throws Exception {

        RecordNotFoundException recordNotFoundException = new RecordNotFoundException("No record found");
        given(mockMessageService.getThread(1L)).willThrow(recordNotFoundException);

        mvc.perform(get("/questions/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.reason", is(recordNotFoundException.getMessage())));

        verify(mockMessageService, times(1)).getThread(1L);
        verifyNoMoreInteractions(mockMessageService);
    }
}