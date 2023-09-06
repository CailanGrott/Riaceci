package com.cailangrott.riaceci.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class ResourceNotFoundException extends ErrorResponseException {
    public ResourceNotFoundException() {
        super(HttpStatus.NOT_FOUND, construirProblemDetail(), null);
    }

    private static ProblemDetail construirProblemDetail() {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Product not found");
    }
}