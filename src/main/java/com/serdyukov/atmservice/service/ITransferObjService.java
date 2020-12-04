package com.serdyukov.atmservice.service;

import com.serdyukov.atmservice.dto.touser.TransactionDTO;
import com.serdyukov.atmservice.entity.Transaction;

import java.util.List;
import java.util.Set;

public interface ITransferObjService {
    Set<TransactionDTO> toTransactionDTOSet(List<Transaction> transactions);

}
