package com.andpostman.xmlfilereader.file;

import com.andpostman.xmlfilereader.service.ArtemisProducer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import com.andpostman.xmlfilereader.generated.DocumentBatch;

import java.io.File;


@Service
@Slf4j
@RequiredArgsConstructor
public class FileXmlParser {

    private final ArtemisProducer producer;

    public Flux<Void> parseFileToSend(File file){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DocumentBatch.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            DocumentBatch documentBatch = (DocumentBatch) un.unmarshal(file);
            return Flux.fromIterable(documentBatch.getDocumentRecord()).flatMap(producer::sendMessageBrokerXmlType);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return Flux.empty();
    }
}
