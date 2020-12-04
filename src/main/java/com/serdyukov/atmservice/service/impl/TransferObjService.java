package com.serdyukov.atmservice.service.impl;

import com.serdyukov.atmservice.dto.touser.TransactionDTO;
import com.serdyukov.atmservice.entity.Transaction;
import com.serdyukov.atmservice.service.ITransferObjService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TransferObjService implements ITransferObjService {

    @Override
    public Set<TransactionDTO> toTransactionDTOSet(List<Transaction> transactionList) {
        return transactionList
                .stream()
                .filter(Objects::nonNull)
                .map(t -> toTransactionDto(t))
                .collect(Collectors.toSet());
    }

    private TransactionDTO toTransactionDto(Transaction t) {
        return new TransactionDTO(t.getTransactionType(), t.getValue(), t.getTimestamp(), t.getReason());
    }


}
