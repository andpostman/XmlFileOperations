package com.andpostman.documenthandler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class DocumentHandlerApplicationTests {

    @Test
    void contextLoads() {
        Integer[] array = {1, 2, 3, 4, 5};

        Flux.range(0, array.length)
                .flatMap(index -> {
                    Integer element = array[index];
                    // Your code logic here
                    return Mono.just(element);
                })
                .subscribe(result -> System.out.println(result));
    }

}
