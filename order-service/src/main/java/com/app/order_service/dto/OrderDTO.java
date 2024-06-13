package com.app.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String orderId;
   // private String customerId;
  //  private String productId;
    private String orderName;
    private int quantity;
    private double price;
    private LocalDateTime orderDate;
   // private String status;
}
