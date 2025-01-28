package com.example.demo.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

@Configuration
public class ActiveMQConfig {

    private static final String BROKER_URL = "tcp://localhost:61616"; // Change if needed
    private static final String BROKER_USERNAME = "admin"; // Default ActiveMQ username
    private static final String BROKER_PASSWORD = "admin"; // Default ActiveMQ password

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_USERNAME, BROKER_PASSWORD, BROKER_URL);
        factory.setTrustedPackages(Arrays.asList("com.example.demo")); 
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionAcknowledgeMode(1); // AUTO_ACKNOWLEDGE
        return jmsTemplate;
    }
}

