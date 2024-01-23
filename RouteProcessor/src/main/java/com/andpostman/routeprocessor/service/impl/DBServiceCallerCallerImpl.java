package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Persistable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.service.DBServiceCaller;


@Service
@RequiredArgsConstructor
@Slf4j
public class DBServiceCallerCallerImpl implements DBServiceCaller {

    private final PersonDBServiceImpl personDBService;
    private final CardDBServiceImpl cardDBService;
    private final CurrencyFormDBServiceImpl currencyFormDBService;
    private final CurTypeDepDBServiceImpl curTypeDepDBService;
    private final CodeCurDepDBServiceImpl codeCurDepDBService;
    private final XcodeCurDBServiceImpl xcodeCurDBService;


    @Override
    public Mono<? extends Persistable<Integer>> saveInDb(LotusDocumentType document) {
        if (document.getForm().equals("PERSON")){
            return personDBService.createPerson(document);
        }
        else if (document.getForm().equals("CARD")){
            return cardDBService.createCard(document);
        }

        return currencyFormDBService.createCurrencyForm(document)
                .map(currencyFormDto -> {
                    for (LotusDocumentType.Field field: document.getField()
                    ) {
                        switch (field.getName()) {
                            case "curTypeDep" -> {
                                log.info("curTypeDep: {}", field.getItem());
                                curTypeDepDBService.createCurTypeDep(field.getItem(), currencyFormDto.getId()).subscribe();
                            } case "codeCurDep" -> {
                                log.info("codeCurDep: {}", field.getItem());
                                codeCurDepDBService.createCodeCurDep(field.getItem(), currencyFormDto.getId()).subscribe();
                            } case "xcodeCurDep" -> {
                                log.info("xcodeCurDep: {}", field.getItem());
                                xcodeCurDBService.createXcodeCurDep(field.getItem(), currencyFormDto.getId()).subscribe();
                            }
                        }
                    }
                    return currencyFormDto;
                });
    }

}
