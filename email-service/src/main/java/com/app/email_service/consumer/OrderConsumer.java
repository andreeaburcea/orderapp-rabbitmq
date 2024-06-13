package com.app.email_service.consumer;

import com.app.email_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    //subscribe to the queue to consume the event
    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void consume(OrderEvent event) {
        //logged the event to the console.
        LOGGER.info(String.format("Order event received in email service => %s ", event.toString()));

        //email service needs to send an  email costumer.
        // write it
        // Generate email content based on the event
        String recipientEmail = event.getStatus(); // Assuming OrderEvent has a getCustomerEmail method
        String subject = "Your Order Confirmation";
        String emailContent = generateEmailContent(event);

        // Send the email
        EmailSender.sendEmail(recipientEmail, subject, emailContent);
    }

    private String generateEmailContent(OrderEvent event) {
        // Construct the email content using the event details
        return String.format("Dear %s,\n\nThank you for your order! Here are your order details:\n\nOrder ID: %s\nTotal Amount: $%.2f\n\nBest regards,\nYour Company",
                event.getStatus(),
                event.getMessage());

    }
    }

