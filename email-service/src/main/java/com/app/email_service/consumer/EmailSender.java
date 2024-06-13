package com.app.email_service.consumer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendEmail(String recipientEmail, String subject, String emailContent) {
        // Set up properties for the mail session
        Properties props = new Properties();

        props.put("mail.smtp.host", "your_smtp_host"); // Change this to your SMTP server
        props.put("mail.smtp.port", "your_smtp_port"); // Change this to your SMTP port
        props.put("mail.smtp.auth", "true"); // If your SMTP server requires authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Create a mail session
        Session session = Session.getInstance(props, null);

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress("your_email_address"));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(emailContent);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
    }
}
