package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.model.CardDto;
import com.andpostman.routeprocessor.repository.CardRepository;
import com.andpostman.routeprocessor.service.CardDBService;
import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardDBServiceImpl implements CardDBService {

    private final CardRepository repository;

    @Override
    public Mono<CardDto> createCard(LotusDocumentType document) {

        CardDto cardDto = new CardDto();
        for (LotusDocumentType.Field field: document.getField()
        ) {
            String fieldValue = field.getItem().stream().findFirst().get();
            for (Field cardField: cardDto.getClass().getDeclaredFields()
                 ) {
                cardField.setAccessible(true);
                if (cardField.getName().equals(field.getName())) {
                    try {
                        cardField.set(cardDto,fieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        log.info("Card: {}",cardDto);
        return repository.save(cardDto);
    }
}
