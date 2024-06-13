package com.app.stock_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "orders")
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private String orderId;
        // private String customerId;
        //  private String productId;
        @Column(name = "order_name")
        private String orderName;
        @Column(name = "quantity")
        private int quantity;
        @Column(name = "price")
        private double price;
        @Column(name = "order_date")
        private LocalDateTime orderDate;
        // private String status;
}
