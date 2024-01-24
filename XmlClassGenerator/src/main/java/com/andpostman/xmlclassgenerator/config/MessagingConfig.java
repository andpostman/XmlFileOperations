package com.andpostman.xmlclassgenerator.config;

import com.andpostman.xmlclassgenerator.generated.DocumentRecord;
import jakarta.jms.Destination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {

    @Value("${jms.queue.destination}")
    private String destinationQueue;

    @Bean
    public Destination packageQueue(){
        return new ActiveMQQueue(destinationQueue);
    }

    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("document", DocumentRecord.class);
        messageConverter.setTypeIdMappings(typeIdMappings);
        return messageConverter;
    }
}
