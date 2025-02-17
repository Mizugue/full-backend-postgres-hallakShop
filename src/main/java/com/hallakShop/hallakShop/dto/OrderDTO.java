package com.hallakShop.hallakShop.dto;

import com.hallakShop.hallakShop.entities.Order;
import com.hallakShop.hallakShop.entities.OrderItem;
import com.hallakShop.hallakShop.entities.OrderStatus;
import com.hallakShop.hallakShop.entities.Payment;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;
    private ClientDTO client;
    private PaymentDTO payment;
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment, List<OrderItemDTO> items) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
        this.items = items;
    }

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public PaymentDTO getPayment() {
        return this.payment != null ? this.payment : new PaymentDTO();

    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items.addAll(items);

    }

    public Double getTotal(){
        double sum = 0;
        for (OrderItemDTO item : items){
            sum += item.getSubTotal();
        }
        return sum;
    }


}
