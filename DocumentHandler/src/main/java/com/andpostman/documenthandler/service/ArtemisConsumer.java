package com.andpostman.documenthandler.service;

import com.andpostman.documenthandler.property.DocumentRecord;
import com.andpostman.documenthandler.service.impl.FileRecorderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtemisConsumer {

    private final FileRecorderServiceImpl service;

    @JmsListener(destination = "${jms.queue.destination.response}")
    @JmsListener(destination = "${jms.queue.destination.error}")
    public void receiveMessage(DocumentRecord document,
                               @Headers MessageHeaders headers){
        String header = headers.get("STATUS", String.class);
        log.info("get message from artemis: {},{}", header, document.getRecId());
        service.saveFile(header, document).subscribe();
    }

}
