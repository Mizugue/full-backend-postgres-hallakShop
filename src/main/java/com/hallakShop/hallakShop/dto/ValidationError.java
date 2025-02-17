package com.hallakShop.hallakShop.dto;



import org.aspectj.apache.bcel.classfile.Field;

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

    public void addError(String fieldName, String message){
        errors.removeIf(x -> x.getFieldName().equals(fieldName));
        errors.add(new FieldMessage(fieldName, message));
    }
}
