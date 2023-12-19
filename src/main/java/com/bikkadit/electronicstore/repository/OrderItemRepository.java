package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
