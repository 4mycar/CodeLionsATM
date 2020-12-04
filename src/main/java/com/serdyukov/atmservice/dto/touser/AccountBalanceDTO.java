package com.serdyukov.atmservice.dto.touser;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountBalanceDTO {

    private Double amount;
    private LocalDateTime timestamp;

}
