package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import org.springframework.data.domain.Persistable;
import reactor.core.publisher.Mono;

public interface DBServiceCaller {
    Mono<? extends Persistable<Integer>> saveInDb(LotusDocumentType document);
}
