package com.app.order_service.controller;

import com.app.order_service.dto.OrderDTO;
import com.app.order_service.dto.OrderEvent;
import com.app.order_service.exception.OrderException;
import com.app.order_service.publisher.OrderProducer;
import com.app.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    private OrderProducer orderProducer;
    private OrderService orderService;
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    //convert object in java object from json
    // http://localhost:8080/api/orders
    //see in the rabbitmq plugin UI
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
        //assign random id to the order
        orderDTO.setOrderId(UUID.randomUUID().toString());
        OrderEvent event = new OrderEvent();
        event.setStatus("PENDING");
        event.setMessage("Order is in pending status..");
        event.setOrderDTO(orderDTO);
        //we send event to the order producer
        orderProducer.sendMessage(event);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order sent to the RabbitMQ");
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable String orderId, @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String orderId) {
        try {
            OrderDTO orderDTO = orderService.getOrderById(orderId);
            return ResponseEntity.ok(orderDTO);
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId) {
        try {
            orderService.deleteOrderById(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (OrderException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
