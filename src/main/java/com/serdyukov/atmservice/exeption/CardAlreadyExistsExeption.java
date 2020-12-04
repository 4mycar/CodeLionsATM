package com.serdyukov.atmservice.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class CardAlreadyExistsExeption extends RuntimeException {

    public CardAlreadyExistsExeption(String s) {
        super(s);
    }
}

