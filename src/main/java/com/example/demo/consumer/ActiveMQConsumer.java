package com.example.demo.consumer;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class);

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                logger.info("Received message: {}", message);
            } else {
                logger.warn("Received non-text message: {}", message);
            }
        } catch (JMSException e) {
            logger.error("Error processing message", e);
        }
    }
}
