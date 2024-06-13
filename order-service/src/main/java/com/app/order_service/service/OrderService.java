package com.app.order_service.service;

import com.app.order_service.dto.OrderDTO;
import com.app.order_service.dto.OrderEvent;
import com.app.order_service.entity.Order;
import com.app.order_service.exception.OrderException;
import com.app.order_service.mapper.OrderMapper;
import com.app.order_service.publisher.OrderProducer;
import com.app.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderProducer orderProducer;
    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    public OrderService(OrderProducer orderProducer, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderProducer = orderProducer;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }



    public void createOrder(OrderEvent orderEvent) {
        // Business logic for creating an order
        // E.g., validate order, save to database, etc.

        Order order = orderMapper.toOrder(orderEvent.getOrderDTO());
        orderRepository.save(order);
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order created successfully");

        // Publish order event
        orderProducer.sendMessage(orderEvent);

    }

    public OrderDTO updateOrder(String orderId, OrderDTO orderDTO) throws OrderException {
        Order existingOrder = orderRepository.findById(orderId).get();

        if (existingOrder == null) {
            throw new OrderException("Order not found with id: " + orderId);
        }

        existingOrder.setOrderName(orderDTO.getOrderName());
        existingOrder.setPrice(orderDTO.getPrice());
        existingOrder.setQuantity(orderDTO.getQuantity());
        existingOrder.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : existingOrder.getOrderDate());

        Order updatedOrder = orderRepository.save(existingOrder);

        if (updatedOrder == null) {
            throw new OrderException("Service.UPDATE_FAILED");
        }

        OrderDTO updatedOrderDTO = orderMapper.toOrderDTO(updatedOrder);

        OrderEvent orderEvent = orderMapper.toOrderEvent(updatedOrder, "UPDATED", "Order updated successfully");
        orderProducer.sendMessage(orderEvent);

        return updatedOrderDTO;
//
    }
    public OrderDTO getOrderById(String orderId) throws OrderException {
        // Fetch the order by ID
        Order order = orderRepository.getOne(orderId);

        // Check if the order is null
        if (order == null) {
            throw new OrderException("Order not found with id: " + orderId);
        }

        // Convert the order to DTO and return it
        return orderMapper.toOrderDTO(order);
    }

    public void deleteOrderById(String orderId) throws OrderException {
        // Fetch the order by ID
        Order order = orderRepository.getOne(orderId);

        // Check if the order is null
        if (order == null) {
            throw new OrderException("Order not found with id: " + orderId);
        }

        // Delete the order
        orderRepository.delete(order);

        // Send order event for deletion
        OrderEvent orderEvent = orderMapper.toOrderEvent(order, "DELETED", "Order deleted successfully");
        orderProducer.sendMessage(orderEvent);
    }
}
