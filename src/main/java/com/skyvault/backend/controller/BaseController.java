package com.skyvault.backend.controller;

import com.skyvault.backend.exception.ProblemDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<ProblemDetail> createErrorResponse(HttpStatus status, String message) {
        ProblemDetail problem = new ProblemDetail();
        problem.setStatus(status.value());
        problem.setDetail(message);
        problem.setTitle(status.getReasonPhrase());
        return ResponseEntity.status(status).body(problem);
    }
}