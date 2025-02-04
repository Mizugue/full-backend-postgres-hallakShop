package com.hallakShop.hallakShop.entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor

@EqualsAndHashCode
@Entity
@Table(name = "tb_order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    @Getter
    @Setter
    private Integer quantity;

    @Getter
    @Setter
    private Double price;

    public OrderItem(Order order, Product product, Integer quantity, Double price){
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;

    }

    public Order getOrder(){
        return id.getOrder();
    }

    public Product getProduct(){
        return id.getProduct();
    }

    public void setProduct(Product product){
        id.setProduct(product);
    }

    public void setOrder(Order order){
        id.setOrder(order);
    }

}
