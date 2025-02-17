package com.hallakShop.hallakShop.repositories;

import com.hallakShop.hallakShop.entities.Order;
import com.hallakShop.hallakShop.entities.OrderItem;
import com.hallakShop.hallakShop.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
