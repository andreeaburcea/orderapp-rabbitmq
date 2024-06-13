package com.app.email_service.config;

//we already created a queue in the order service
// in our stock service we don't need to do it again because
// stock service is going to read the order event in JSON format
//that's why we need to configure the message converter.

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// send a message and then email will consume
@Configuration
public class RabbitMQConfig {
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    //convert java object to json
    //convert json to jo
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
