package com.serdyukov.atmservice.controller;

import com.serdyukov.atmservice.dto.touser.AccountBalanceDTO;
import com.serdyukov.atmservice.security.JwtTokenProvider;
import com.serdyukov.atmservice.service.impl.AccountService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Api(tags = "Account Controller")
public class AccountController {

    private AccountService accountService;
    private JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Get a balance for account")
    @GetMapping(value = "/account/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountBalanceDTO> getAccountBalance(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (isValidHeader(token)) {
            Long cardId = Long.valueOf(jwtTokenProvider.getLoginFromJwt(token));
            return ResponseEntity.ok(accountService.getAmountDTOById(cardId));
        }
        return ResponseEntity.badRequest().build();

    }

    private boolean isValidHeader(String token) {
        return !token.isEmpty() && jwtTokenProvider.validateToken(token);
    }
}
