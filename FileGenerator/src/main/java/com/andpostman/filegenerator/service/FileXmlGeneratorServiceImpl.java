package com.andpostman.filegenerator.service;

import com.andpostman.filegenerator.generated.*;
import com.andpostman.filegenerator.property.Card;
import com.andpostman.filegenerator.property.CurrencyForm;
import com.andpostman.filegenerator.property.JmsMessage;
import com.andpostman.filegenerator.property.Person;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Slf4j
public class FileXmlGeneratorServiceImpl {

    @Value("${file.generate.count}")
    private Integer countToGenerate;

    @Value("${file.generate.capacity}")
    private Integer capacity;

    @Value("${file.generate.gen-source}")
    private String genSource;

    private AtomicInteger generator = new AtomicInteger();

    public void generate(){

        /**
         * 3 типа и их размножаем:
         * 1 - 1 DocumentRecord VALID
         * 2 - 3 DocumentRecord VALID
         * 3 - 1 DocumentRecord XSD_ERROR
         * 4 - 1 DocumentRecord FORM_TYPE_ERROR
         * 5 - capacity DocumentRecord VALID;
         **/

        String[] paths = {"CARD","PERSON","CURRENCY_FORM"};
        GenerationType.VALID_CAPACITY.setCount(capacity);
        List<GenerationType> generationTypes = List.of(
                GenerationType.VALID_ONE,
                GenerationType.VALID_THREE,
                GenerationType.XSD_ERROR,
                GenerationType.FORM_TYPE_ERROR

        );

//        Flux.fromArray(paths)
//                .flatMap(path ->{
//                        Flux.zip(
//                                genDocuments(path,GenerationType.VALID_CAPACITY),
//                                Flux.fromIterable(generationTypes)
//                                        .flatMap(type ->
//                                            Flux.range(1, countToGenerate)
//                                                    .flatMap(gen -> genDocuments(path,type))
        for (String path: paths
             ) {
            genDocuments(path,GenerationType.VALID_CAPACITY);
            for (GenerationType type: generationTypes
                 ) {
                for (int i = 0; i < countToGenerate; i++) {
                    genDocuments(path,type);
                }
            }
        }
    }

    private void genDocuments(String path, GenerationType type){
        List<DocumentRecord> records = new ArrayList<>();
        String newSource = genSource + path + "/" + type.name() + generator.incrementAndGet() + ".xml";
        log.info("new Source:{}",newSource);

        for (int j = 0; j < type.getCount(); j++) {
            LotusDocumentType document = new ObjectFactory().createLotusDocumentType();
            document.setUNID("SBFGGC8E33245C6FC32573E6013XEG"+new SplittableRandom().nextInt(1, 100));
            document.setSourceSystemName(path+type.name());
            document.setEventType(type.name());
            String FORMATER = "yyyy-MM-dd'T'HH:mm:ss";

            DateFormat format = new SimpleDateFormat(FORMATER);

            Date date = new Date();
            XMLGregorianCalendar gDateFormatted =
                    null;
            try {
                gDateFormatted = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
//            log.info("TIME:{}",gDateFormatted);
            document.setEventDateTime(gDateFormatted);
            if (type.name().equals("FORM_TYPE_ERROR"))
                document.setForm("");
            else document.setForm(path);
            document.setFilePath(newSource);
            document.setDbReplicaID(String.valueOf(j));
            document.setDBName("UGP2000");
            document.setField(setDocumentFieldsByPath(path, type));

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
            records.add(documentRecord);
        }
        saveFile(path,newSource,records,type);
    }

    private void saveFile(String path,String newSource,List<DocumentRecord> records, GenerationType type){
        DocumentBatch batch = new ObjectFactory().createDocumentBatch();
        byte[] batchId = new byte[10];
        byte[] sessionId = new byte[10];
        new SplittableRandom().nextBytes(batchId);
        new SplittableRandom().nextBytes(sessionId);
        batch.setBatchId(batchId);
        batch.setSessionId(sessionId);
        batch.setServerName("UGP00" + new SplittableRandom().nextInt(1, 10));
        batch.setProblem(type.name());
        batch.setFilePath(path);
        batch.setDbReplicaID(String.valueOf(new SplittableRandom().nextInt(1, 100)));
        batch.setDBName("UGP2000");
        batch.setDocumentRecord(records);

        try {
            JAXBContext context = JAXBContext.newInstance(DocumentBatch.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //mb need to create file earlier and dir
            File newFile = new File(newSource);

            Mono.just(newFile.getParentFile().mkdirs()).subscribe();
            Mono.just(newFile.createNewFile()).subscribe();
            marshaller.marshal(batch, newFile);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
//        return Mono.empty();
    }

    private List<LotusDocumentType.Field> setDocumentFieldsByPath(String path, GenerationType type){
        if (path.equals("CARD")){
            return initCard(type);
        } else if (path.equals("CURRENCY_FORM")){
            return initCurrencyForm(type);
        } else {
            return initPerson(type);
        }
    }

    private <T extends JmsMessage> List<LotusDocumentType.Field> createDocumentFields (GenerationType generationType, T object){
        Field[] fields = object.getClass().getDeclaredFields();
        Integer xsdErrorFieldNumber = null;
        if (generationType.name().equals("XSD_ERROR")){
            xsdErrorFieldNumber = new SplittableRandom().nextInt(0,fields.length);
        }
        List<LotusDocumentType.Field> list = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field cardField = fields[i];
            cardField.setAccessible(true);
            try {
                String fieldName = cardField.getName();
                Object fieldValue = cardField.get(object);
                LotusDocumentType.Field field = new ObjectFactory().createLotusDocumentTypeField();
                if(xsdErrorFieldNumber == null || xsdErrorFieldNumber != i){
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
        }
        return list;
    }

    private List<LotusDocumentType.Field> initCard(GenerationType generationType){
        Card card = Card.builder()
                .nameJur("Наименование11")
                .type("Юр.Лицо")
                .tmpPrizn("Резидент")
                .prizn("0")
                .forma("ООО")
                .formaFull("ООО")
                .INN("7704835739")
                .KPP("772601001")
                .OKPO("http://www.w3.org/2001/XMLSchema-instance")
                .act("1")
                .tmpCardType("http://www.w3.org/2001/XMLSchema-instance")
                .nameJurEng("poiizon drop")
                .PBOYuL("http://www.w3.org/2001/XMLSchema-instance")
                .birthday("http://www.w3.org/2001/XMLSchema-instance")
                .grade("http://www.w3.org/2001/XMLSchema-instance")
                .lastName("http://www.w3.org/2001/XMLSchema-instance")
                .firstName("http://www.w3.org/2001/XMLSchema-instance")
                .middleName("http://www.w3.org/2001/XMLSchema-instance")
                .title("http://www.w3.org/2001/XMLSchema-instance")
                .lastNameE("http://www.w3.org/2001/XMLSchema-instance")
                .firstNameE("http://www.w3.org/2001/XMLSchema-instance")
                .middleNameE("http://www.w3.org/2001/XMLSchema-instance")
                .country("РФ")
                .zip("-")
                .region("-")
                .area("-")
                .town("МОСКВА")
                .street("ХОЛОДИЛЬНЫЙ ПЕР")
                .home("3")
                .building("http://www.w3.org/2001/XMLSchema-instance")
                .flat("http://www.w3.org/2001/XMLSchema-instance")
                .liveCountry("РФ")
                .liveZip("-")
                .liveRegion("-")
                .liveArea("-")
                .liveTown("МОСКВА")
                .liveStreet("ХОЛОДИЛЬНЫЙ ПЕР")
                .liveHome("3")
                .liveBuilding("http://www.w3.org/2001/XMLSchema-instance")
                .liveFlat("http://www.w3.org/2001/XMLSchema-instance")
                .countryE("rf")
                .zipD("http://www.w3.org/2001/XMLSchema-instance")
                .regionE("http://www.w3.org/2001/XMLSchema-instance")
                .areaE("rf")
                .townE("MOSCOW")
                .streetE("http://www.w3.org/2001/XMLSchema-instance")
                .homeE("http://www.w3.org/2001/XMLSchema-instance")
                .buildingE("http://www.w3.org/2001/XMLSchema-instance")
                .flatE("http://www.w3.org/2001/XMLSchema-instance")
                .locations("-, РФ, -, -, МОСКВА, ХОЛОДИЛЬНЫЙ ПЕР, 3")
                .unid("http://www.w3.org/2001/XMLSchema-instance")
                .docPar("http://www.w3.org/2001/XMLSchema-instance")
                .linkLN("http://www.w3.org/2001/XMLSchema-instance")
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

    private List<LotusDocumentType.Field> initPerson(GenerationType generationType){
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

    private List<LotusDocumentType.Field> initCurrencyForm(GenerationType generationType){
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
