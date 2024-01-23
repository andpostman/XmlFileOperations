package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.exception.XmlErrorHandler;
import com.andpostman.routeprocessor.property.DocumentRecord;
import com.andpostman.routeprocessor.property.LogResponse;
import com.andpostman.routeprocessor.service.ArtemisProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.config.DestinationConfig;
import com.andpostman.routeprocessor.service.ResultService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService {

    private final ArtemisProducer producer;
    private final DestinationConfig destination;
    private final DBServiceCallerCallerImpl dbService;
    private final LogResponseServiceImpl logResponseService;

    @Override
    public Mono<Void> sendValidResult(DocumentRecord document){

        return dbService.saveInDb(document.getDocument()).map(
                model -> {
                    logResponseService.printLogResponse(
                            LogResponse.builder()
                                    .status(LogResponse.Status.SUCCESS)
                                    .unid(document.getDocument().getUNID())
                                    .message(model.toString())
                                    .build()
                    );
                    return model;
                }).then(producer.sendMessageBroker(destination.getResponse(),LogResponse.Status.SUCCESS, document));
    }

    @Override
    public Mono<Void> sendXsdErrorResult(DocumentRecord document, XmlErrorHandler xsdErrorHandler) {
        xsdErrorHandler.getExceptions().forEach(e ->
                logResponseService.printLogResponse(LogResponse.builder()
                        .status(LogResponse.Status.XSD_ERROR)
                        .message(e.getCause().toString().split("org.xml.sax.SAXParseException;")[1])
                        .unid(document.getDocument().getUNID())
                        .build()
                ));
        return producer.sendMessageBroker(destination.getError(), LogResponse.Status.XSD_ERROR, document);
    }

    @Override
    public Mono<Void> sendFormTypeErrorResult(DocumentRecord document) {
        logResponseService.printLogResponse(
                LogResponse.builder()
                        .status(LogResponse.Status.TYPE_ERROR)
                        .unid(document.getDocument().getUNID())
                        .message(LogResponse.Status.TYPE_ERROR.name())
                        .build()
        );
        return producer.sendMessageBroker(destination.getError(),LogResponse.Status.TYPE_ERROR, document);
    }
}
