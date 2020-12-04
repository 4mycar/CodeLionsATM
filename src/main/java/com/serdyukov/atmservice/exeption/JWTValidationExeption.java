package com.serdyukov.atmservice.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JWTValidationExeption extends RuntimeException {
    public JWTValidationExeption(String s) {
        super(s);
    }
}
