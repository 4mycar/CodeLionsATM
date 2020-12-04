package com.serdyukov.atmservice.service;

import com.serdyukov.atmservice.entity.Account;
import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.exeption.AccountNotFoundException;
import com.serdyukov.atmservice.repository.IAccountRepository;
import com.serdyukov.atmservice.service.impl.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private IAccountRepository accountRepository = Mockito.mock(IAccountRepository.class);
    private AccountService cut = new AccountService(accountRepository);

    @Test
    void findById() {
        Long cardId = 1111222233334444L;
        String pin = "1234";
        Optional<Account> account = Optional.of(new Account(1L, new Card(cardId, pin), 100.0));
        Mockito.when(accountRepository.findByCard_Id(cardId)).thenReturn(account);
        Account act = cut.findById(cardId);
        assertEquals(act.getAmount(), 100.0);
    }

    @Test
    void findByIdFail() {
        Long cardId = 1111L;
        Mockito.when(accountRepository.findByCard_Id(cardId)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> cut.findById(cardId));
    }
}