package com.serdyukov.atmservice.dto.fromuser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

@Value
public class UserRegDto {

    String adminKey;

    @Range(min = 1000_0000_0000_0000L, max = 9999_9999_9999_9999L, message = "Card must have 16 dights")
    Long cardID;

    @Range(min = 1000, max = 9999, message = "Length of pin must be 4 dights")
    int pin;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserRegDto(@JsonProperty("adminKey") String adminKey,
                      @JsonProperty("cardID") Long cardID,
                      @JsonProperty("pin") int pin) {
        this.adminKey = adminKey;
        this.cardID = cardID;
        this.pin = pin;
    }
}

