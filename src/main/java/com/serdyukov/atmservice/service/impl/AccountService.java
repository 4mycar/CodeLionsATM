package com.serdyukov.atmservice.service.impl;

import com.serdyukov.atmservice.dto.touser.AccountBalanceDTO;
import com.serdyukov.atmservice.entity.Account;
import com.serdyukov.atmservice.exeption.AccountNotFoundException;
import com.serdyukov.atmservice.repository.IAccountRepository;
import com.serdyukov.atmservice.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Account findById(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByCard_Id(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new AccountNotFoundException(String.format("Account %s not found", id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAmountById(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByCard_Id(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get().getAmount();
        } else {
            throw new AccountNotFoundException(String.format("Account %s not found", id));
        }
    }


    @Transactional(readOnly = true)
    public AccountBalanceDTO getAmountDTOById(Long id) throws AccountNotFoundException {
        return new AccountBalanceDTO(getAmountById(id), LocalDateTime.now());
    }


}
