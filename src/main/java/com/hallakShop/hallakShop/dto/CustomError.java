package com.hallakShop.hallakShop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String trace;

    public CustomError(Instant timestamp, Integer status, String error, String trace) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.trace = trace;
    }
}
