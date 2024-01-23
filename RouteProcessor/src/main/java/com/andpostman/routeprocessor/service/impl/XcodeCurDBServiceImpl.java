package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.repository.XcodeCurDepRepository;
import com.andpostman.routeprocessor.service.XcodeCurDepDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import com.andpostman.routeprocessor.model.XcodeCurDep;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class XcodeCurDBServiceImpl implements XcodeCurDepDBService {

    private final XcodeCurDepRepository repository;

    @Override
    public Flux<XcodeCurDep> createXcodeCurDep(List<String>list, int currencyForm) {
        return Flux.fromIterable(list)
                .flatMap(element -> repository.save(new XcodeCurDep(element,currencyForm)))
                .publishOn(Schedulers.parallel());
    }
}
