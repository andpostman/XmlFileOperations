package com.andpostman.routeprocessor.service;

import reactor.core.publisher.Flux;
import com.andpostman.routeprocessor.model.CodeCurDep;
import java.util.List;

public interface CodeCurDepDBService {
    Flux<CodeCurDep>createCodeCurDep(List<String>list, int currencyFormId);
}
