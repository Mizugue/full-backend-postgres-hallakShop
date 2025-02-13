package com.hallakShop.hallakShop.dto;


import jakarta.validation.constraints.*;



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

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO() {
    }

    public Long getId() {
        return id;
    }


    public @NotNull @NotEmpty @NotBlank @Size(min = 3, max = 80, message = "3-8!") String getName() {
        return name;
    }

    public void setName(@NotNull @NotEmpty @NotBlank @Size(min = 3, max = 80, message = "3-8!") String name) {
        this.name = name;
    }

    public @Size(min = 5, message = "min 5") @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 5, message = "min 5") @NotBlank String description) {
        this.description = description;
    }

    public @Positive(message = "The price must be positive") Double getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "The price must be positive") Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
