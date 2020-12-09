package com.serdyukov.atmservice.exeption;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(
            AccountNotFoundException e) {

        return new ResponseEntity<>(getExceptionBodyWithMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardAlreadyExistsExeption.class)
    public ResponseEntity<Object> handleCardAlreadyExistsExeption(
            CardAlreadyExistsExeption e) {

        return new ResponseEntity<>(getExceptionBodyWithMessage(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedExeption.class)
    public ResponseEntity<Object> handleAccessDeniedExeption(
            AccessDeniedExeption e) {

        return new ResponseEntity<>(getExceptionBodyWithMessage(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<Object> handleNegativeAmountException(
            NegativeAmountException e) {

        return new ResponseEntity<>(getExceptionBodyWithMessage(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JWTValidationExeption.class)
    public ResponseEntity<Object> handleJWTValidationExeption(
            JWTValidationExeption e) {

        return new ResponseEntity<>(getExceptionBodyWithMessage(e.getMessage()), HttpStatus.FORBIDDEN);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("reason", e.getMessage());

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getExceptionBodyWithMessage(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        return body;
    }
}