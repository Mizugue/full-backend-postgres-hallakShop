package com.hallakShop.hallakShop.services.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg){
        super(msg);

    }
}


