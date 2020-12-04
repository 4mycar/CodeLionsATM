package com.serdyukov.atmservice.service.impl;

import com.serdyukov.atmservice.dto.fromuser.UserRegDto;
import com.serdyukov.atmservice.entity.Account;
import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.exeption.AccessDeniedExeption;
import com.serdyukov.atmservice.exeption.AccountNotFoundException;
import com.serdyukov.atmservice.exeption.CardAlreadyExistsExeption;
import com.serdyukov.atmservice.repository.IAccountRepository;
import com.serdyukov.atmservice.repository.ICardRepository;
import com.serdyukov.atmservice.service.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService implements ICardService {

    private ICardRepository cardRepository;
    private IAccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void addNewCard(UserRegDto userRegDto) {
        Long id = userRegDto.getCardID();
        int pin = userRegDto.getPin();
        if (cardRepository.existsById(id)) {
            throw new CardAlreadyExistsExeption(String.format("Card with ID %s already exists", id));
        }
        Card card = new Card(id, passwordEncoder.encode(String.valueOf(pin)));
        Account account = new Account(card, 0.0);
//        cardRepository.save(card);
        accountRepository.save(account);
    }

    @Override
    public Card findCardByIDAndPin(Long id, int pin) {
        Card card = getCardByIDElseThrow(id);
        if (card != null && passwordEncoder.matches(String.valueOf(pin), card.getPin())) {
            return card;
        } else {
            throw new AccessDeniedExeption("Wrong password");
        }

    }

    public Card getCardByIDElseThrow(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) {
            throw new AccountNotFoundException(String.format("Card with ID %s not found", id));
        } else {
            return card.get();
        }
    }
}
