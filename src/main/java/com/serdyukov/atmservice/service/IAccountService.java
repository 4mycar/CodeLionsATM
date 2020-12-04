package com.serdyukov.atmservice.service;

import com.serdyukov.atmservice.entity.Account;
import com.serdyukov.atmservice.exeption.AccountNotFoundException;

public interface IAccountService {
    Account findById(Long id) throws AccountNotFoundException;
    Double getAmountById (Long id) throws AccountNotFoundException;
}
