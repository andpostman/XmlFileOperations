package com.andpostman.documenthandler.config;

import com.andpostman.documenthandler.property.DocumentRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
public class MessagingConfig {

    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("out", DocumentRecord.class);
        messageConverter.setTypeIdMappings(typeIdMappings);
        return messageConverter;
    }

}

