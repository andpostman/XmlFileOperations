package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.DocumentRecord;
import reactor.core.publisher.Mono;

public interface ValidationServiceCaller {
    Mono<Void> validateMessage(DocumentRecord document);
}
