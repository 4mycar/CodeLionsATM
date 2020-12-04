package com.serdyukov.atmservice.service;

import com.serdyukov.atmservice.dto.fromuser.TransactionRequestDTO;
import com.serdyukov.atmservice.entity.Account;
import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.enums.TransactionType;
import com.serdyukov.atmservice.repository.IAccountRepository;
import com.serdyukov.atmservice.repository.ICardRepository;
import com.serdyukov.atmservice.repository.ITransactionRepository;
import com.serdyukov.atmservice.service.impl.AccountService;
import com.serdyukov.atmservice.service.impl.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class TransactionServiceTest {

    private IAccountRepository accountRepository = Mockito.mock(IAccountRepository.class);
    private ITransactionRepository transactionRepository = Mockito.mock(ITransactionRepository.class);
    private AccountService accountService = Mockito.mock(AccountService.class);
    private ICardRepository cardRepository = Mockito.mock(ICardRepository.class);
    private ITransferObjService transferObjService = Mockito.mock(ITransferObjService.class);
    private TransactionService cut = new TransactionService(transactionRepository, accountRepository, cardRepository, accountService, transferObjService);


    @Test
    void getTransactionHistory() {
    }

    @Test
    void getTransaction() {
    }

    @Test
    void createCreditTransactionSuccess() {
        Long id = 1111222233334444L;
        String pin = "1234";
        Card card = new Card(id, pin);
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(100.0, "test");
        Account account = new Account(1L, card, 50.0);
        Mockito.when(accountService.findById(id)).thenReturn(account);
        Mockito.when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        Mockito.when(accountService.getAmountById(id)).thenReturn(account.getAmount());
        cut.createTransaction(transactionRequestDTO, id, TransactionType.CREDIT);
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }
    @Test
    void createDebitTransactionFail() {
        Long id = 1111222233334444L;
        String pin = "1234";
        Card card = new Card(id, pin);
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(100.0, "test");
        Account account = new Account(new Card(id, pin), 50.0);
        Mockito.when(accountService.findById(id)).thenReturn(account);
        Mockito.when(accountService.getAmountById(id)).thenReturn(account.getAmount());
        Mockito.when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        cut.createTransaction(transactionRequestDTO, id, TransactionType.CREDIT);
        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }
}