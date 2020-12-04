package com.serdyukov.atmservice.service.impl;

import com.serdyukov.atmservice.dto.fromuser.TransactionRequestDTO;
import com.serdyukov.atmservice.dto.fromuser.TransferRequestDTO;
import com.serdyukov.atmservice.dto.touser.TransactionDTO;
import com.serdyukov.atmservice.entity.Account;
import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.entity.Transaction;
import com.serdyukov.atmservice.enums.TransactionType;
import com.serdyukov.atmservice.exeption.AccountNotFoundException;
import com.serdyukov.atmservice.exeption.InvalidOperationException;
import com.serdyukov.atmservice.exeption.NegativeAmountException;
import com.serdyukov.atmservice.repository.IAccountRepository;
import com.serdyukov.atmservice.repository.ICardRepository;
import com.serdyukov.atmservice.repository.ITransactionRepository;
import com.serdyukov.atmservice.service.IAccountService;
import com.serdyukov.atmservice.service.ITransactionService;
import com.serdyukov.atmservice.service.ITransferObjService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.serdyukov.atmservice.enums.TransactionType.CREDIT;
import static com.serdyukov.atmservice.enums.TransactionType.DEBIT;

@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService {


    private ITransactionRepository transactionRepository;
    private IAccountRepository accountRepository;
    private ICardRepository cardRepository;
    private IAccountService accountService;
    private ITransferObjService transferObjService;


    @Override
    public Set<TransactionDTO> getTransactionHistory(Long id) throws InvalidOperationException {
        List<Transaction> transactions = transactionRepository.findAllByCard_Id(id);
        return transferObjService.toTransactionDTOSet(transactions);

    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = InvalidOperationException.class)
    @Override
    public void createTransaction(TransactionRequestDTO transactionRequestDTO, Long id, TransactionType operation) throws InvalidOperationException {

        Double value = transactionRequestDTO.getValue();
        String reason = transactionRequestDTO.getReason();

        switch (operation) {
            case DEBIT:
                saveDebitTransaction(id, value, reason);
                break;
            case CREDIT:
                saveCreditTransaction(id, value, reason);
                break;
            default:
                throw new InvalidOperationException("");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = InvalidOperationException.class)
    @Override
    public void createTransfer(TransferRequestDTO transferRequestDTO, Long id) throws InvalidOperationException {
        Long destCardId = transferRequestDTO.getCardId();
        Double value = transferRequestDTO.getValue();
        String reason = transferRequestDTO.getReason();
        if (findCardById(destCardId) != null) {
            saveDebitTransaction(id, value, reason);
            saveCreditTransaction(destCardId, value, reason);
        }

    }

    private void saveDebitTransaction(Long id, Double value, String reason) {
        if (accountService.getAmountById(id) < value) {
            throw new NegativeAmountException(String.format("An account %s has less amount that you try to debit", id));
        } else {
            Card card = findCardById(id);
            Transaction transaction = new Transaction(card, DEBIT, value, reason);
            Account account = accountService.findById(id);
            transactionRepository.save(transaction);
            account.setAmount(account.getAmount() - value);
            accountRepository.save(account);
        }

    }

    private void saveCreditTransaction(Long id, Double value, String reason) {
        Card card = findCardById(id);
        Transaction transaction = new Transaction(card, CREDIT, value, reason);
        Account account = accountService.findById(id);
        transactionRepository.save(transaction);
        account.setAmount(account.getAmount() + value);
        accountRepository.save(account);
    }

    public Card findCardById(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) {
            throw new AccountNotFoundException(String.format("Account %s not found", id));
        } else {
            return card.get();
        }
    }


}
