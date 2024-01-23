package com.andpostman.routeprocessor.service;

import reactor.core.publisher.Flux;
import com.andpostman.routeprocessor.model.CurTypeDep;
import java.util.List;

public interface CurTypeDepDBService {
    Flux<CurTypeDep> createCurTypeDep(List<String> list, int currencyFormId);
}
