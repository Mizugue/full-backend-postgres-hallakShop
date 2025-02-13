package com.hallakShop.hallakShop.dto;



import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class ValidationError extends CustomError{

    private final List <FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String trace) {
        super(timestamp, status, error, trace);
    }

    public void setErrors(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }
}
