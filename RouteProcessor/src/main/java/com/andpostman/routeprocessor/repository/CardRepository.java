package com.andpostman.routeprocessor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andpostman.routeprocessor.model.CardDto;

public interface CardRepository extends ReactiveCrudRepository<CardDto, Integer> {
}
