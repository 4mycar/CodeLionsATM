package com.serdyukov.atmservice.dto.touser;

import com.serdyukov.atmservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private TransactionType transactionType;
    private Double value;
    private LocalDateTime timestamp;
    private String reason;

}
