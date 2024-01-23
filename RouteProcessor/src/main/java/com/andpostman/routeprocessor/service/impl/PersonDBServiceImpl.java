package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.property.LotusDocumentType;
import com.andpostman.routeprocessor.repository.PersonRepository;
import com.andpostman.routeprocessor.service.PersonDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.model.PersonDto;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonDBServiceImpl implements PersonDBService {

    private final PersonRepository personRepository;

    @Override
    public Mono<PersonDto> createPerson(LotusDocumentType document) {
        PersonDto personDto = new PersonDto();
        for (LotusDocumentType.Field field: document.getField()
             ) {
            String fieldValue = field.getItem().stream().findFirst().get();
            switch (field.getName()){
                case "dateBorn" -> personDto.setDateBorn(
                        LocalDate.parse(
                                fieldValue,DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        )
                );
                case "firstName" -> personDto.setFirstName(fieldValue);
                case "lastName" -> personDto.setLastName(fieldValue);
                case "middleName" -> personDto.setMiddleName(fieldValue);
                case "unid" -> personDto.setUnid(fieldValue);
                case "internetAddress" -> personDto.setInternetAddress(fieldValue);
                case "revisions" -> personDto.setModifiedDateTime(
                        LocalDateTime.parse(
                                fieldValue, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                        )
                );
                case "contactMan" -> personDto.setInsertUser(fieldValue);
            }
        }
        personDto.setInsertDate(
                LocalDateTime.parse(
                        new SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
                                .format(Calendar.getInstance().getTime()), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                )
        );
        log.info("Person: {}",personDto);
        return personRepository.save(personDto);
//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//        PersonDto personDto = new PersonDto(
//                LocalDate.parse(person.getDateBorn(), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
//                person.getFirstName(),
//                person.getLastName(),
//                person.getMiddleName(),
//                person.getUnid(),
//                person.getInternetAddress(),
//                LocalDateTime.parse(person.getRevisions(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
//                LocalDateTime.parse(dateFormat.format(date), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
//                person.getContactMan()
//        );
//        return personRepository.save(personDto);
    }
}
