package com.andpostman.routeprocessor.service;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.model.CurrencyFormDto;

public interface CurrencyFormDBService {
    Mono<CurrencyFormDto>createCurrencyForm(LotusDocumentType document);
}
