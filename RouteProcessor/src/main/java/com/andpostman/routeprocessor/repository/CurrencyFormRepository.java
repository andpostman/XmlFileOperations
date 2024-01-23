package com.andpostman.routeprocessor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andpostman.routeprocessor.model.CurrencyFormDto;

public interface CurrencyFormRepository extends ReactiveCrudRepository<CurrencyFormDto, Integer> {
}
