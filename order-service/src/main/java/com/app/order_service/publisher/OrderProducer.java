package com.app.order_service.publisher;

import com.app.order_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// we defined the class as spring bean
@Service
public class OrderProducer {

    // log the statements
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    // to send the event to the exchange, we need an exchange and routing key to route
    // this event to the queue.

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    private RabbitTemplate rabbitTemplate;
    // spring constructor based dependency injection.
    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent) {
        // first log the event
        LOGGER.info(String.format("Order Event sent to RabbitMQ => %s", orderEvent));
        //send order event to order queue
        rabbitTemplate.convertAndSend(exchange, orderRoutingKey, orderEvent);
        //send order event to email queue
        rabbitTemplate.convertAndSend(exchange, emailRoutingKey, orderEvent);

    }




}
