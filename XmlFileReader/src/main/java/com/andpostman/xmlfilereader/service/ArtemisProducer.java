package com.andpostman.xmlfilereader.service;

import jakarta.jms.Destination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.andpostman.xmlfilereader.generated.DocumentRecord;


@Service
@Slf4j
@RequiredArgsConstructor
public class ArtemisProducer {

    private final JmsTemplate jmsTemplate;
    private final Destination destination;

    public Mono<Void> sendMessageBrokerXmlType(DocumentRecord fileMessage) {
        jmsTemplate.convertAndSend(destination, fileMessage);
        log.info("send message from ArtemisProducer: {}",fileMessage);
        return Mono.empty();
    }
}
