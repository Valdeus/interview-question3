package com.example.demo.web.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ErrorResult {
    private final Boolean success = false;
    private String reason;
}
