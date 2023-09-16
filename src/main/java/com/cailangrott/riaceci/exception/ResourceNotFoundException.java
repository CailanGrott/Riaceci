package com.cailangrott.riaceci.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class ResourceNotFoundException extends ErrorResponseException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, buildProblemDetail(message), null);
    }

    private static ProblemDetail buildProblemDetail(String detail) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail);
    }
}