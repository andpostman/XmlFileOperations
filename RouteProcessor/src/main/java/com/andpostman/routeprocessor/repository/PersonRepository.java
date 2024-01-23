package com.andpostman.routeprocessor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andpostman.routeprocessor.model.PersonDto;

public interface PersonRepository extends ReactiveCrudRepository<PersonDto, Integer> {
}
