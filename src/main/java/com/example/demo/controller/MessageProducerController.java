package com.example.demo.controller;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer")
public class MessageProducerController {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "test-queue";

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        try {
            // Create Connection Factory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE_NAME);

            // Create Producer & Message
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(message);

            // Send Message
            producer.send(textMessage);

            // Clean up
            producer.close();
            session.close();
            connection.close();

            return "Message sent: " + message;
        } catch (JMSException e) {
            e.printStackTrace();
            return "Failed to send message";
        }
    }
}
