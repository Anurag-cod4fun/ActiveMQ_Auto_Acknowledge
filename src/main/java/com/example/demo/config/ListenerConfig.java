package com.example.demo.config;

import jakarta.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Configuration;

import com.example.demo.consumer.ActiveMQConsumer;

@Configuration
public class ListenerConfig {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "test-queue";

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Queue queue;
    private MessageConsumer consumer;

    public ListenerConfig() {
        try {
            // Create Connection Factory
            connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

            // Create Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(QUEUE_NAME);

            // Create Consumer
            consumer = session.createConsumer(queue);
            consumer.setMessageListener(new ActiveMQConsumer());

            System.out.println("ActiveMQ Consumer is listening...");

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
