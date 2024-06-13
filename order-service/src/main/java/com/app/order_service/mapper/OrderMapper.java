package com.app.order_service.mapper;

import com.app.order_service.dto.OrderDTO;
import com.app.order_service.dto.OrderEvent;
import com.app.order_service.entity.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class OrderMapper {

    public Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setOrderName(orderDTO.getOrderName());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        // Set the order date to the current date if needed
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderName(order.getOrderName());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setOrderDate(order.getOrderDate());
        return orderDTO;
    }

    public OrderEvent toOrderEvent(Order order, String status, String message) {
        OrderDTO orderDTO = toOrderDTO(order);
        return new OrderEvent(status, message, orderDTO);
    }
}