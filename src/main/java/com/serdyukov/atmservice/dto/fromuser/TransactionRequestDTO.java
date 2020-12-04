package com.serdyukov.atmservice.dto.fromuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    @Range(min = 0, max = 149500, message = "You can deposit only less than 149500 UAH")
    private Double value;
    private String reason;
}
