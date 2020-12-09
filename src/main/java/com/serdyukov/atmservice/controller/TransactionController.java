package com.serdyukov.atmservice.controller;

import com.serdyukov.atmservice.dto.fromuser.TransactionRequestDTO;
import com.serdyukov.atmservice.dto.fromuser.TransferRequestDTO;
import com.serdyukov.atmservice.dto.touser.TransactionDTO;
import com.serdyukov.atmservice.enums.TransactionType;
import com.serdyukov.atmservice.security.JwtTokenProvider;
import com.serdyukov.atmservice.service.impl.AccountService;
import com.serdyukov.atmservice.service.impl.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@AllArgsConstructor
@Api(tags = "Transaction Controller")
@RequestMapping("/account/transactions")
public class TransactionController {

    private AccountService accountService;
    private TransactionService transactionService;
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Returns transaction history by account")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<TransactionDTO>> transactionsByAccountId(HttpServletRequest httpServletRequest) {
        Long cardId = jwtTokenProvider.getCardIdFromRequest(httpServletRequest);
        Set<TransactionDTO> transactions = transactionService.getTransactionHistory(cardId);
        return ResponseEntity.of(Optional.of(transactions));
    }

    @Operation(summary = "Deposit money to account")
    @PostMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> depositMoney(HttpServletRequest httpServletRequest,
                                        @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        Long cardId = jwtTokenProvider.getCardIdFromRequest(httpServletRequest);
        transactionRequestDTO.setReason("Deposit money: " + transactionRequestDTO.getReason());
        transactionService.createTransaction(transactionRequestDTO, cardId, TransactionType.CREDIT);
        return ResponseEntity.ok().build();

    }

    @Operation(summary = "Withdraw money from account")
    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> withdrawMoney(HttpServletRequest httpServletRequest,
                                         @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        Long cardId = jwtTokenProvider.getCardIdFromRequest(httpServletRequest);
        transactionRequestDTO.setReason("Withdraw money: " + transactionRequestDTO.getReason());
        transactionService.createTransaction(transactionRequestDTO, cardId, TransactionType.DEBIT);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Transfer  money between accounts")
    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> transferMoney(HttpServletRequest httpServletRequest,
                                         @Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        Long cardId = jwtTokenProvider.getCardIdFromRequest(httpServletRequest);
        transferRequestDTO.setReason("Transfer money to card  " + transferRequestDTO.getCardId());
        transactionService.createTransfer(transferRequestDTO, cardId);
        return ResponseEntity.ok().build();

    }


}
