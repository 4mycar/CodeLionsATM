package com.serdyukov.atmservice.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedExeption extends RuntimeException {

    public AccessDeniedExeption(String s) {
        super(s);
    }
}
