package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.repository.CurTypeDepRepository;
import com.andpostman.routeprocessor.service.CurTypeDepDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import com.andpostman.routeprocessor.model.CurTypeDep;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurTypeDepDBServiceImpl implements CurTypeDepDBService {

    private final CurTypeDepRepository repository;

    @Override
    public Flux<CurTypeDep> createCurTypeDep(List<String>list, int currencyFormId) {
        return Flux.fromIterable(list)
                .flatMap(element -> repository.save(new CurTypeDep(element, currencyFormId)))
                .subscribeOn(Schedulers.parallel());
    }
}
