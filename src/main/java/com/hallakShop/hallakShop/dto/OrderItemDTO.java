package com.hallakShop.hallakShop.dto;

import com.hallakShop.hallakShop.entities.OrderItem;

public class OrderItemDTO {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;

    public OrderItemDTO(Long productId, String name, Double price, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItemDTO(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.name = orderItem.getProduct() != null ? orderItem.getProduct().getName() : "Nome Indisponível";
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
}

    public OrderItemDTO() {
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal(){
        return price * quantity;
    }
}
