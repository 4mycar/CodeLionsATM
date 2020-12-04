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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    ResponseEntity<Set<TransactionDTO>> transactionsByAccountId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (isValidHeader(token)) {
            Long cardId = Long.valueOf(jwtTokenProvider.getLoginFromJwt(token));
            Set<TransactionDTO> transactions = transactionService.getTransactionHistory(cardId);
            return ResponseEntity.ok(transactions);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Deposit money to account")
    @PostMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> depositMoney(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        if (isValidHeader(token)) {
            Long cardId = Long.valueOf(jwtTokenProvider.getLoginFromJwt(token));
            transactionRequestDTO.setReason("Deposit money: " + transactionRequestDTO.getReason());
            transactionService.createTransaction(transactionRequestDTO, cardId, TransactionType.CREDIT);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Withdraw money from account")
    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> withdrawMoney(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        if (isValidHeader(token)) {
            Long cardId = Long.valueOf(jwtTokenProvider.getLoginFromJwt(token));
            transactionRequestDTO.setReason("Withdraw money: " + transactionRequestDTO.getReason());
            transactionService.createTransaction(transactionRequestDTO, cardId, TransactionType.DEBIT);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Transfer  money between accounts")
    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> transferMoney(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        if (isValidHeader(token)) {
            Long cardId = Long.valueOf(jwtTokenProvider.getLoginFromJwt(token));
            transferRequestDTO.setReason("Transfer money to card  " + transferRequestDTO.getCardId());
            transactionService.createTransfer(transferRequestDTO, cardId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    private boolean isValidHeader(String token) {
        return !token.isEmpty() && jwtTokenProvider.validateToken(token);
    }

}
