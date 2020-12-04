package com.serdyukov.atmservice.dto.touser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AuthResponse(@JsonProperty("token") String token) {
        this.token = token;
    }
}
