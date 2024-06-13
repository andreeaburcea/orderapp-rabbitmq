package com.app.email_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
   // private Date orderDate;
   // private String status;
}
