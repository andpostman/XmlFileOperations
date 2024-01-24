package com.andpostman.xmlclassgenerator.service;

import com.andpostman.xmlclassgenerator.generated.DocumentRecord;
import com.andpostman.xmlclassgenerator.generated.GenerationType;
import com.andpostman.xmlclassgenerator.generated.LotusDocumentType;
import com.andpostman.xmlclassgenerator.generated.ObjectFactory;
import com.andpostman.xmlclassgenerator.property.Card;
import com.andpostman.xmlclassgenerator.property.CurrencyForm;
import com.andpostman.xmlclassgenerator.property.JmsMessage;
import com.andpostman.xmlclassgenerator.property.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentsGeneratorServiceImpl {

    @Value("${file.generate.count}")
    private Integer countToGenerate;

    @Value("${file.generate.capacity}")
    private Integer capacity;

    private final ArtemisProducer producer;


    public Flux<Void> generateDocuments(){

//        String[] models = {"CARD","PERSON","CURRENCY_FORM"};
//        GenerationType.VALID_CAPACITY.setCount(capacity);
//        GenerationType [] generationTypes = {
//                GenerationType.VALID_ONE,
//                GenerationType.VALID_THREE,
//                GenerationType.XSD_ERROR,
//                GenerationType.FORM_TYPE_ERROR
//        };
//
//        for (String model: models
//             ) {
//            genDocuments(model,GenerationType.VALID_CAPACITY);
//            for (GenerationType type: generationTypes
//                 ) {
//                for (int i = 0; i < countToGenerate; i++) {
//                    genDocuments(model,type);
//                }
//            }
//        }

        Flux<String> models = Flux.just(
                "CARD"
                , "PERSON"
                , "CURRENCY_FORM"
        );
        Flux<GenerationType> generationTypes = Flux.just(
                GenerationType.VALID_ONE
                , GenerationType.VALID_THREE
                , GenerationType.XSD_ERROR
                , GenerationType.FORM_TYPE_ERROR
        );

        GenerationType.VALID_CAPACITY.setCount(capacity);

//        return models
//                .flatMap(model -> generationTypes
//                        .flatMap(generationType -> Flux.range(1,countToGenerate)
//                                        .flatMap(index -> genDocuments(model, generationType))

//                        .repeat(countToGenerate - 1)
//                        .zipWithIterable(genDocuments(model, GenerationType.VALID_CAPACITY).toIterable())
//                                .zipWith(genDocuments(model, GenerationType.VALID_CAPACITY))

//                                )
//                );

//        return models
//                .flatMap(model -> generationCount
//                        .flatMap(genCount -> Flux.zip(
//                                genDocuments(model, GenerationType.VALID_ONE),
//                                        genDocuments(model, GenerationType.FORM_TYPE_ERROR),
//                                        genDocuments(model, GenerationType.XSD_ERROR))
//                        )
//                );

        return models
                .flatMap(model -> genDocuments(model, GenerationType.VALID_CAPACITY)
                        .thenMany(generationTypes
                                .flatMap(generationType -> Flux.range(1,countToGenerate)
                                        .flatMap(genCount -> genDocuments(model,generationType))
                                )
                        )
                );


//        return flux1
//                .count()
//                .flatMap(el -> flux2
//                        .count()
//                        .flatMap(el2 -> {
//                            if (el > el2){
//                                combinedFlux.mergeWith(flux1.skip(el2)).subscribe();
//                            } else {
//                                combinedFlux.mergeWith(flux2.skip(el)).subscribe();
//                            }
//                            return Mono.just(el);
//                        })
//                ).thenMany(Flux.empty());

//        long flux2L = flux2.count().block();
//        if (flux1L > flux2L) {
//            log.info("flux1");
//            combinedFlux = combinedFlux.mergeWith(flux1.skip(flux2L));
//        } else {
//            log.info("flux2");
//            combinedFlux = combinedFlux.mergeWith(flux2.skip(flux1L));
//        }
//        return combinedFlux;
    }

    private Flux<Void> genDocuments(String model, GenerationType type){
        List<DocumentRecord> records = new ArrayList<>();
        return Flux.range(1, type.getCount())
                .flatMap(el -> generateDocumentAttributes(model, type, el)
                        .flatMap(document -> generateDocumentRecordAttributes(document)
                                .doOnNext(records::add)
//                                .map(records::add)
                        )
                )
                .thenMany(Flux.fromIterable(records).flatMap(producer::sendMessageBrokerXmlType));

//        for (int j = 0; j < type.getCount(); j++) {
//            LotusDocumentType document = generateDocumentAttributes(model, type, j);
//            DocumentRecord documentRecord = generateDocumentRecordAttributes(document);
//            records.add(documentRecord);
//        }
//        return Flux.fromIterable(records).flatMap(producer::sendMessageBrokerXmlType);

//        records.forEach(producer::sendMessageBrokerXmlType);
    }

    private Mono <DocumentRecord> generateDocumentRecordAttributes(LotusDocumentType document){
        DocumentRecord documentRecord = new ObjectFactory().createDocumentRecord();
//            if (!type.name().equals("XSD_ERROR")) {
//                byte[] recId = new byte[1];
//                new SplittableRandom().nextBytes(recId);
//                documentRecord.setRecId(recId);
//            }
        byte[] recId = new byte[10];
        new SplittableRandom().nextBytes(recId);
        documentRecord.setRecId(recId);
        documentRecord.setDocument(document);
        return Mono.just(documentRecord);
    }

    private Mono<LotusDocumentType> generateDocumentAttributes(String model, GenerationType type, int number){
        String pathSource = File.separator + model + File.separator + type.name() + number + ".xml";
        LotusDocumentType document = new ObjectFactory().createLotusDocumentType();
        document.setUNID("SBFGGC8E33245C6FC32573E6013XEG" + new SplittableRandom().nextInt(1, 100));
        document.setSourceSystemName(model+"_"+type.name());
        document.setEventType(type.name());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        XMLGregorianCalendar gDateFormatted = null;
        try {
            gDateFormatted = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        document.setEventDateTime(gDateFormatted);
        if (type.name().equals("FORM_TYPE_ERROR"))
            document.setForm("");
        else document.setForm(model);
        document.setFilePath(pathSource);
        document.setDbReplicaID(String.valueOf(number));
        document.setDBName("UGP2000");
        List<LotusDocumentType.Field> fields = new ArrayList<>();

        return setDocumentFieldsByPath(model,type)
                .doOnNext(fields::add)
//                .map(fields::add)
                .then(Mono.fromRunnable(() -> document.setField(fields)))
                .then(Mono.just(document));

//        document.setField(fields);
//        return Mono.just(document);
    }


    private Flux<LotusDocumentType.Field> setDocumentFieldsByPath(String model, GenerationType type){
        if (model.equals("CARD")){
            return initCardFields(type);
        } else if (model.equals("CURRENCY_FORM")){
            return initCurrencyFormFields(type);
        } else {
            return initPersonFields(type);
        }
    }

    private <T extends JmsMessage> Flux<LotusDocumentType.Field> createDocumentFields (GenerationType generationType, T object){
        Field[] fields = object.getClass().getDeclaredFields();
        AtomicInteger xsdErrorFieldNumber = null;
        if (generationType.name().equals("XSD_ERROR")){
            xsdErrorFieldNumber = new AtomicInteger(new SplittableRandom().nextInt(0,fields.length));
        }
        List<LotusDocumentType.Field> list = new ArrayList<>();
        AtomicInteger finalXsdErrorFieldNumber = xsdErrorFieldNumber;
        return Flux.fromArray(fields)
                .index()
                .flatMap(model -> {
                    Field modelField = model.getT2();
                    long modelNumber = model.getT1();
                    modelField.setAccessible(true);
                    return Mono.fromRunnable(() -> {
                        try {
                            String fieldName = modelField.getName();
                            Object fieldValue = modelField.get(object);
                            LotusDocumentType.Field field = new ObjectFactory().createLotusDocumentTypeField();
                            if(finalXsdErrorFieldNumber == null || finalXsdErrorFieldNumber.get() != modelNumber){
                                field.setType("string");
                                field.setName(fieldName);
                                field.setAlias("string");
                                List<String> items;
                                if (fieldValue instanceof Collection){
                                    items = new ArrayList<>((Collection<String>) fieldValue);
                                    field.setItem(items);
                                } else field.setItem(List.of(fieldValue.toString()));
                            }
                            list.add(field);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });
                })
                .thenMany(Flux.fromIterable(list));
//        for (int i = 0; i < fields.length; i++) {
//            Field modelField = fields[i];
//            modelField.setAccessible(true);
//            try {
//                String fieldName = modelField.getName();
//                Object fieldValue = modelField.get(object);
//                LotusDocumentType.Field field = new ObjectFactory().createLotusDocumentTypeField();
//                if(xsdErrorFieldNumber == null || xsdErrorFieldNumber != i){
//                    field.setType("string");
//                    field.setName(fieldName);
//                    field.setAlias("string");
//                    List<String> items;
//                    if (fieldValue instanceof Collection){
//                        items = new ArrayList<>((Collection<String>) fieldValue);
//                        field.setItem(items);
//                    } else field.setItem(List.of(fieldValue.toString()));
//                }
//
//                list.add(field);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        return Flux.fromIterable(list);
    }

    private Flux<LotusDocumentType.Field> initCardFields(GenerationType generationType){
        final String XML_SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
        Card card = Card.builder()
                .nameJur("Наименование11")
                .type("Юр.Лицо")
                .tmpPrizn("Резидент")
                .prizn("0")
                .forma("ООО")
                .formaFull("ООО")
                .INN("7704835739")
                .KPP("772601001")
                .OKPO(XML_SCHEMA_INSTANCE)
                .act("1")
                .tmpCardType(XML_SCHEMA_INSTANCE)
                .nameJurEng("poiizon drop")
                .PBOYuL(XML_SCHEMA_INSTANCE)
                .birthday(XML_SCHEMA_INSTANCE)
                .grade(XML_SCHEMA_INSTANCE)
                .lastName(XML_SCHEMA_INSTANCE)
                .firstName(XML_SCHEMA_INSTANCE)
                .middleName(XML_SCHEMA_INSTANCE)
                .title(XML_SCHEMA_INSTANCE)
                .lastNameE(XML_SCHEMA_INSTANCE)
                .firstNameE(XML_SCHEMA_INSTANCE)
                .middleNameE(XML_SCHEMA_INSTANCE)
                .country("РФ")
                .zip("-")
                .region("-")
                .area("-")
                .town("МОСКВА")
                .street("ХОЛОДИЛЬНЫЙ ПЕР")
                .home("3")
                .building(XML_SCHEMA_INSTANCE)
                .flat(XML_SCHEMA_INSTANCE)
                .liveCountry("РФ")
                .liveZip("-")
                .liveRegion("-")
                .liveArea("-")
                .liveTown("МОСКВА")
                .liveStreet("ХОЛОДИЛЬНЫЙ ПЕР")
                .liveHome("3")
                .liveBuilding(XML_SCHEMA_INSTANCE)
                .liveFlat(XML_SCHEMA_INSTANCE)
                .countryE("rf")
                .zipD(XML_SCHEMA_INSTANCE)
                .regionE(XML_SCHEMA_INSTANCE)
                .areaE("rf")
                .townE("MOSCOW")
                .streetE(XML_SCHEMA_INSTANCE)
                .homeE(XML_SCHEMA_INSTANCE)
                .buildingE(XML_SCHEMA_INSTANCE)
                .flatE(XML_SCHEMA_INSTANCE)
                .locations("-, РФ, -, -, МОСКВА, ХОЛОДИЛЬНЫЙ ПЕР, 3")
                .unid(XML_SCHEMA_INSTANCE)
                .docPar(XML_SCHEMA_INSTANCE)
                .linkLN(XML_SCHEMA_INSTANCE)
                .idClientUpdateQueue("225771")
                .idClient("342566")
                .modifiedUser("ModifiedUser")
                .urPhone("(000)00000")
                .livePhoneCity("000")
                .phone("00000")
                .isCustomer("1")
                .structure("18. Прочее")
                .structureUnid("EBECCD8C33731C6FC32573E5003CFE21")
                .contrRole("Грузовые услуги")
                .build();
        return createDocumentFields(generationType, card);
    }

    private Flux<LotusDocumentType.Field> initPersonFields(GenerationType generationType){
        Person person = Person.builder()
                .revisions("06.08.2023 05:42:52")
                .additionalInfo("Some additional info")
                .adminPersonUid("92683BC5709EA1EC4325894700327C20")
                .chief("0")
                .contactMan("Las")
                .dateBorn("28.02.2003")
                .firstName("Artur")
                .lastName("Grigoriev")
                .middleName("Arturovich")
                .personId("12345678")
                .title("Повар")
                .unid("20FA05324EAE9D0F432585060012446D")
                .internetAddress("email@dme.ru")
                .phone("88432423432")
                .callSign("222")
                .build();
        return createDocumentFields(generationType, person);
    }

    private Flux<LotusDocumentType.Field> initCurrencyFormFields(GenerationType generationType){
        CurrencyForm currencyForm = CurrencyForm.builder()
                .curType("Японская иена")
                .codeCur("392")
                .xcodeCur("JPY")
                .course("65,1612")
                .curTypeDep(List.of("$","%"))
                .codeCurDep(List.of("643","810"))
                .xcodeCurDep(List.of("RUB","RUR"))
                .date("20.09.2023")
                .cur(100)
                .build();
        return createDocumentFields(generationType, currencyForm);
    }
}
