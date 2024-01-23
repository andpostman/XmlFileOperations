package com.andpostman.routeprocessor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andpostman.routeprocessor.model.XcodeCurDep;

public interface XcodeCurDepRepository extends ReactiveCrudRepository<XcodeCurDep, Integer> {
}
