package com.serdyukov.atmservice.service;

import com.serdyukov.atmservice.dto.fromuser.TransactionRequestDTO;
import com.serdyukov.atmservice.dto.fromuser.TransferRequestDTO;
import com.serdyukov.atmservice.dto.touser.TransactionDTO;
import com.serdyukov.atmservice.enums.TransactionType;
import com.serdyukov.atmservice.exeption.InvalidOperationException;

import java.util.Set;

public interface ITransactionService {

    Set<TransactionDTO> getTransactionHistory(Long id) throws InvalidOperationException;

    void createTransaction(TransactionRequestDTO transactionRequestDTO, Long id, TransactionType operation) throws InvalidOperationException;

    void createTransfer(TransferRequestDTO transferRequestDTO, Long id) throws InvalidOperationException;
}
