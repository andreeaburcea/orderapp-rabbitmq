package com.app.stock_service.dto;

//event class to send to rabbitmq exchange

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//create RabbitMQ producer to send this OrderEvent to the RabbitMQ Topic Exchange
public class OrderEvent {
    private String status; //pending, progress/ completed
    private String message;
    private OrderDTO orderDTO;

}
