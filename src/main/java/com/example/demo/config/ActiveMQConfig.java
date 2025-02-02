package com.example.demo.config;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConfig.class);
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "test-queue";

    private Connection connection;
    private Session session;
    private Queue queue;
    private MessageProducer producer;

    public ActiveMQConfig() {
        try {
            logger.info("ActiveMQConfig: Initializing ActiveMQ persistent connection...");

            // Create Connection Factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

            // Create a SINGLE connection that remains active
            connection = connectionFactory.createConnection();
            connection.start();
            logger.info("ActiveMQConfig: Connection established successfully.");

            // Create a single session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(QUEUE_NAME);
            logger.info("ActiveMQConfig: Queue '{}' initialized.", QUEUE_NAME);

            // Create a SINGLE producer that stays active
            producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT); // Ensures messages are persistent
            logger.info("ActiveMQConfig: Persistent MessageProducer created.");

        } catch (JMSException e) {
            logger.error("ActiveMQConfig: Error initializing ActiveMQ", e);
        }
    }

    public Session getSession() {
        return session;
    }

    public MessageProducer getProducer() {
        return producer;
    }

    public Queue getQueue() {
        return queue;
    }
}
