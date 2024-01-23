package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.exception.XmlErrorHandler;
import com.andpostman.routeprocessor.property.DocumentRecord;
import reactor.core.publisher.Mono;

public interface ResultService {
    Mono<Void> sendValidResult(DocumentRecord document);
    Mono<Void> sendXsdErrorResult(DocumentRecord document, XmlErrorHandler xsdErrorHandler);
    Mono<Void> sendFormTypeErrorResult(DocumentRecord document);
}
