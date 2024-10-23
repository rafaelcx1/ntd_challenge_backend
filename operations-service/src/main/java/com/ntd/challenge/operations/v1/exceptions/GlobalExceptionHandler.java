package com.ntd.challenge.operations.v1.exceptions;

import com.ntd.challenge.operations.v1.exceptions.types.InvalidOperationException;
import com.ntd.challenge.operations.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.operations.v1.exceptions.types.NotLoggedUserException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(400);

        Map<String, Object> fieldErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            fieldErrors.put(fieldName, error.getDefaultMessage());
        });

        problemDetail.setProperty("errors", fieldErrors);

        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail constraintViolationException(ConstraintViolationException ex) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getMessage());
        HashMap<String, Object> properties = new HashMap<>();

        ex.getConstraintViolations()
                .forEach(e -> {
                    properties.put(e.getPropertyPath().toString(), e.getMessage());
                });

        errorDetail.setProperties(properties);

        return errorDetail;
    }

    @ExceptionHandler(NotLoggedUserException.class)
    public ProblemDetail existentUserException(NotLoggedUserException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ProblemDetail invalidOperationException(InvalidOperationException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ProblemDetail notEnoughBalanceException(NotEnoughBalanceException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail globalExceptionHandler(Exception exception) {
        exception.printStackTrace();
        ProblemDetail errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
        errorDetail.setProperty("description", "Unknown internal server error.");

        return errorDetail;
    }
}
