package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.model.PersonDto;

public interface PersonDBService {
    Mono<PersonDto>createPerson(LotusDocumentType document);
}
