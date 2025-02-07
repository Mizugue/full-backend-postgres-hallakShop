package com.hallakShop.hallakShop.dto;


import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class ProductDTO {



    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 3, max = 80, message = "3-8!")
    private String name;

    @Size(min = 5, message = "min 5")
    @NotBlank
    private String description;

    @Positive(message = "The price must be positive")
    private Double price;

    private String imgUrl;

}
