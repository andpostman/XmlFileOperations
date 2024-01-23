package com.andpostman.routeprocessor.config;
import com.andpostman.routeprocessor.property.DocumentRecord;
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

//    @Value("${jms.queue.destination.send}")
//    private String destinationQueue;
//
//    @Bean
//    public Destination packageQueue(){
//        return new ActiveMQQueue(destinationQueue);
//    }

//    @Bean
//    @SuppressWarnings("all")
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory,
//                                                                          ExceptionAdviceHandler errorHandler) {
//        DefaultJmsListenerContainerFactory factory =
//                new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setErrorHandler(errorHandler);
//        return factory;
//    }

    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("document", DocumentRecord.class);
        typeIdMappings.put("out", DocumentRecord.class);
        messageConverter.setTypeIdMappings(typeIdMappings);
        return messageConverter;
    }

}
