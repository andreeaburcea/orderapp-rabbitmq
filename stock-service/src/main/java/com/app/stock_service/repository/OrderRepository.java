package com.app.stock_service.repository;

import com.app.stock_service.dto.OrderEvent;
import com.app.stock_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
