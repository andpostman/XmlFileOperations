package com.andpostman.routeprocessor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andpostman.routeprocessor.model.CodeCurDep;

public interface CodeCurDepRepository extends ReactiveCrudRepository<CodeCurDep, Integer> {
}
