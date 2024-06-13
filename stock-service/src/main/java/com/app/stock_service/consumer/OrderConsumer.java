package com.app.stock_service.consumer;

import com.app.stock_service.dto.OrderEvent;
import com.app.stock_service.entity.Order;
import com.app.stock_service.repository.OrderRepository;
import com.app.stock_service.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    private OrderRepository orderRepository;
    private StockRepository stockRepository;

    public OrderConsumer(OrderRepository orderRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    // to subscribe this method to the queue
    @RabbitListener(queues = "${rabbitmq.queue.order.name}")
    public void consume(OrderEvent orderEvent, Order order) {
        // log order event to the console.
        LOGGER.info(String.format("Order event received => %s", orderEvent.toString()));

        //save this event data in a database.
        //configure MySQL database to save it.

        try {
            // save order event to the database
            orderRepository.save(order);
        } catch (Exception exception) {
            LOGGER.error("Error saving order event to the database", exception);
        }
    }
}
