package com.example.demo.web;

import com.example.demo.domain.Message;
import com.example.demo.domain.Reply;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/questions")
public class MessageController {
    @Autowired
    private MessageService service;

    @PostMapping
    public ResponseEntity createQuestion(@RequestBody Message question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createQuestion(question));
    }

    @PostMapping("/{questionId}/reply")
    public ResponseEntity createReply(@PathVariable("questionId") Long questionId, @RequestBody Reply reply) {
        reply.setQuestionId(questionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createReply(reply));
    }

    @GetMapping
    public ResponseEntity getAllQuestions() throws RecordNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllQuestions());
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getAllQuestions(@PathVariable("questionId") Long questionId) throws RecordNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(service.getThread(questionId));
    }
}
