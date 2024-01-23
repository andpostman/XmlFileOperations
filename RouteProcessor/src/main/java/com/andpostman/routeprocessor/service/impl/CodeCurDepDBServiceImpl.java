package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.repository.CodeCurDepRepository;
import com.andpostman.routeprocessor.service.CodeCurDepDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import com.andpostman.routeprocessor.model.CodeCurDep;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeCurDepDBServiceImpl implements CodeCurDepDBService {

    private final CodeCurDepRepository repository;

    @Override
    public Flux<CodeCurDep> createCodeCurDep(List<String>list, int currencyFormId) {
        return Flux.fromIterable(list)
                .flatMap(element ->
                    repository.save(new CodeCurDep(element,currencyFormId))
                ).subscribeOn(Schedulers.parallel());
    }
}
