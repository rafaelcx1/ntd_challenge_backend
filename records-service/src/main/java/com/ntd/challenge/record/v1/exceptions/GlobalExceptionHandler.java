package com.ntd.challenge.record.v1.exceptions;

import com.ntd.challenge.record.v1.exceptions.types.NotEnoughBalanceException;
import com.ntd.challenge.record.v1.exceptions.types.RecordNotFoundException;
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

    @ExceptionHandler(RecordNotFoundException.class)
    public ProblemDetail recordNotFoundException(RecordNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ProblemDetail notEnoughBalanceException(NotEnoughBalanceException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
        problemDetail.setTitle(NotEnoughBalanceException.CODE);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail exception(Exception exception) {
        exception.printStackTrace();
        return ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
    }
}
