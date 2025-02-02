package com.example.demo.consumer;

import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQConsumer implements MessageListener, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class);
    private final Session session;
    private final MessageConsumer consumer;

    public ActiveMQConsumer(Session session, Queue queue) throws JMSException {
        this.session = session;
        this.consumer = session.createConsumer(queue);
        this.consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                logger.info("Consumer: Received message: {}", textMessage.getText());
            } else {
                logger.warn("Consumer: Received non-text message.");
            }
        } catch (JMSException e) {
            logger.error("Consumer: Error processing message", e);
        }
    }

    @Override
    public void run() {
        try {
            logger.info("Consumer: Listening for messages...");
            while (true) {
                Thread.sleep(1000); // Keep the thread alive
            }
        } catch (InterruptedException e) {
            logger.error("Consumer thread interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
