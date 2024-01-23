package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.model.CardDto;

public interface CardDBService {
    Mono<CardDto>createCard(LotusDocumentType document);
}
