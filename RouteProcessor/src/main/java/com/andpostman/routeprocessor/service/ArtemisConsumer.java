package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.DocumentRecord;
import com.andpostman.routeprocessor.service.impl.ValidationServiceCallerImpl;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@EnableAsync
public class ArtemisConsumer {

    private final ValidationServiceCallerImpl service;
    private final AtomicInteger consumeMessageNumber;

    @Autowired
    public ArtemisConsumer(ValidationServiceCallerImpl service,@Autowired(required = false) MeterRegistry meterRegistry) {
        this.consumeMessageNumber = new AtomicInteger();
        meterRegistry.gauge("consume_number",consumeMessageNumber);
        this.service = service;
    }

    @Async
    @JmsListener(destination = "${jms.queue.destination.listen}")
    public void receive(DocumentRecord message){
        log.info("get message from artemis: {}", message.toString());
        consumeMessageNumber.incrementAndGet();
        service.validateMessage(message).subscribe();
    }

    @Async
    @Scheduled(fixedRateString = "${scheduler.contract.timeout}")
    public void sendMetric(){
        consumeMessageNumber.get();
    }

}
