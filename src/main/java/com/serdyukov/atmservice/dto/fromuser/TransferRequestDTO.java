package com.serdyukov.atmservice.dto.fromuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    @Range(min = 1000_0000_0000_0000L, max = 9999_9999_9999_9999L, message = "Card must have 16 dights")
    private Long cardId;
    @Range(min = 0, max = 149500, message = "You can deposit only less than 149500 UAH")
    private Double value;
    private String reason;
}
