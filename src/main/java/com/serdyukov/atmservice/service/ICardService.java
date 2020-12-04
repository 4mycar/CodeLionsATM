package com.serdyukov.atmservice.service;


import com.serdyukov.atmservice.dto.fromuser.UserRegDto;
import com.serdyukov.atmservice.entity.Card;

public interface ICardService {
    void addNewCard(UserRegDto userRegDto);
    Card findCardByIDAndPin(Long id, int pin);
}
