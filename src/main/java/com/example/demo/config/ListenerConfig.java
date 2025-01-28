package com.example.demo.config;

import jakarta.jms.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.consumer.ActiveMQConsumer;

@Configuration
public class ListenerConfig {

    @Bean
    public MessageConsumer registerListener(ConnectionFactory connectionFactory) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test-queue");
        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(new ActiveMQConsumer());

        return consumer; // Return the consumer as a bean
    }
}