package com.hallakShop.hallakShop.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends CustomError{

    private final List <FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String trace) {
        super(timestamp, status, error, trace);
    }

    public void setErrors(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }



}
