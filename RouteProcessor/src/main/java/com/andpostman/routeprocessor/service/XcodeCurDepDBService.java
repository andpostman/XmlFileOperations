package com.andpostman.routeprocessor.service;

import reactor.core.publisher.Flux;
import com.andpostman.routeprocessor.model.XcodeCurDep;
import java.util.List;

public interface XcodeCurDepDBService {
    Flux<XcodeCurDep>createXcodeCurDep(List<String>list,int currencyForm);
}
