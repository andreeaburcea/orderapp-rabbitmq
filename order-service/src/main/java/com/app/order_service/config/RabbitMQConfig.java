package com.app.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//this class now is spring based configuration
//before running rabbitmq configuration make sure the rabbitmq server is running in docker desktop
// docker run -d --name rabbitmq-container -p 5672:5672 -p 15672:15672 rabbitmq:3.13.2-management
@Configuration
public class RabbitMQConfig {
    // create spring beans from RabbitMQ


    @Value("${rabbitmq.queue.order.name}")
    private String orderQueue;

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;


    //spring bean for queue - order queue.
    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }
    // spring bean for exchange.
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    // spring bean for binding between exchange and queue using routing key.
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRoutingKey);
    }

    // message converter
    // send order event as JSON to the queue and then consumer will consume that JSON from queue
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    //configure RabbitTemplate for JSON
    //we set the converter to rabbit template config
    // use connection to conenct them
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //set a message converter that is jackson2jsonmessage convert
        //rabbit template will convert it
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    //configure email queue
    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue);
    }

    // spring bean for binding between exchange and queue using routing key.
    @Bean
    public Binding bindingEmail() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }
}
