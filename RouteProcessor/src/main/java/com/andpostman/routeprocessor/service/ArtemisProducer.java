package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.DocumentRecord;
import com.andpostman.routeprocessor.property.LogResponse;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;


@Service
@Slf4j
@EnableAsync
public class ArtemisProducer {

    private final JmsTemplate jmsTemplate;
    private final AtomicInteger sendSuccessMessageNumber;
    private final AtomicInteger sendErrorMessageNumber;

    @Autowired
    public ArtemisProducer(@Autowired(required = false) JmsTemplate jmsTemplate ,@Autowired(required = false) MeterRegistry meterRegistry) {
        this.jmsTemplate = jmsTemplate;
        this.sendSuccessMessageNumber = new AtomicInteger();
        this.sendErrorMessageNumber = new AtomicInteger();
        meterRegistry.gauge("send_success_number", sendSuccessMessageNumber);
        meterRegistry.gauge("send_error_number",sendErrorMessageNumber);
    }

    public Mono<Void> sendMessageBroker(String destination, LogResponse.Status header, DocumentRecord message) {
        jmsTemplate.convertAndSend(destination, message, m -> {
//            log.info("sent from ArtemisProducer to: {}", destination);
//            log.info("header sent from ArtemisProducer: {}", header.name());
            log.info("message sent from ArtemisProducer: {}", message);
            m.setStringProperty("STATUS",header.name());
            return m;
        });
        if (header.equals(LogResponse.Status.SUCCESS))
            sendSuccessMessageNumber.incrementAndGet();
        else
            sendErrorMessageNumber.incrementAndGet();
        return Mono.empty();
    }

    @Async
    @Scheduled(fixedRateString = "${scheduler.contract.timeout}")
    public void sendMetric(){
        sendSuccessMessageNumber.get();
        sendErrorMessageNumber.get();
    }

}
