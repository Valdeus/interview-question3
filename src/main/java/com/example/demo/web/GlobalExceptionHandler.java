package com.example.demo.web;

import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.web.payload.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity handleRecordNotFoundException(RecordNotFoundException e) {
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResult(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleRecordNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResult(e.getMessage()));
    }
}
