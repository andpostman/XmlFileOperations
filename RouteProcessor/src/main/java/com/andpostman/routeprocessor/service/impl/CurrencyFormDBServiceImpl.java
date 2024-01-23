package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.model.CurrencyFormDto;
import com.andpostman.routeprocessor.repository.CurrencyFormRepository;
import com.andpostman.routeprocessor.service.CurrencyFormDBService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyFormDBServiceImpl implements CurrencyFormDBService {

    private final CurrencyFormRepository repository;

    @Override
    public Mono<CurrencyFormDto> createCurrencyForm(LotusDocumentType document) {
        CurrencyFormDto currencyFormDto = new CurrencyFormDto();
        for (LotusDocumentType.Field field : document.getField()) {
            String fieldValue = field.getItem().stream().findFirst().get();
            switch (field.getName()) {
                case "curType" -> currencyFormDto.setCurType(fieldValue);
                case "codeCur" -> currencyFormDto.setCodeCur(fieldValue);
                case "xcodeCur" -> currencyFormDto.setXcodeCur(fieldValue);
                case "course" -> currencyFormDto.setCourse(fieldValue);
                case "date" -> currencyFormDto.setDate(
                        LocalDate.parse(
                                fieldValue, DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        )
                );
                case "cur" -> currencyFormDto.setCur(Integer.parseInt(fieldValue));
            }
        }
        log.info("CurrencyForm: {}",currencyFormDto);
        return repository.save(currencyFormDto);
    }

//    @Override
//    public Mono<CurrencyFormDto> createCurrencyForm(LotusDocumentType document) {
//        CurrencyFormDto formDto = new CurrencyFormDto(
//                currencyForm.getCurType(),
//                currencyForm.getCodeCur(),
//                currencyForm.getXcodeCur(),
//                currencyForm.getCourse(),
//                LocalDate.parse(currencyForm.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
//                currencyForm.getCur()
//        );
//        return repository.save(formDto);
//    }

}
