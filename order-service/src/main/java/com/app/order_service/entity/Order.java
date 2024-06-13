package com.app.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


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
