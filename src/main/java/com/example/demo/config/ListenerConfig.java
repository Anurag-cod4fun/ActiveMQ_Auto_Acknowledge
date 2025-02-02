package com.example.demo.config;

import jakarta.jms.*;
import com.example.demo.consumer.ActiveMQConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ListenerConfig.class);
    private final ActiveMQConfig activeMQConfig;

    public ListenerConfig(ActiveMQConfig activeMQConfig) {
        this.activeMQConfig = activeMQConfig;
        initializeConsumer();
    }

    private void initializeConsumer() {
        try {
            logger.info("ListenerConfig: Initializing ActiveMQ consumer...");
            Session session = activeMQConfig.getSession();
            Queue queue = activeMQConfig.getQueue();

            ActiveMQConsumer consumer = new ActiveMQConsumer(session, queue);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();

            logger.info("ListenerConfig: Consumer started successfully.");
        } catch (JMSException e) {
            logger.error("ListenerConfig: Error initializing consumer", e);
        }
    }
}
