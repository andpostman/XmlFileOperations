package com.andpostman.documenthandler.service.impl;

import com.andpostman.documenthandler.property.DocumentRecord;
import com.andpostman.documenthandler.property.LotusDocumentType;
import jakarta.annotation.PreDestroy;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.andpostman.documenthandler.service.FileRecorderService;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileRecorderServiceImpl implements FileRecorderService {
    @Value("${datasource.xml.write.response.path}")
    private String responseDirectory;

    @Value("${datasource.xml.write.error.path}")
    private String errorDirectory;

    private AtomicInteger formErrorCounter = new AtomicInteger();
    private AtomicInteger xsdErrorCounter = new AtomicInteger();
    private AtomicInteger successCounter = new AtomicInteger();

    @Override
    public Mono<Void> saveFile(String status, DocumentRecord documentRecord){
        if (status.equals("SUCCESS")){
            log.info("go to build: {},{}",successCounter.get(),documentRecord.getRecId());
            return builderDoc(documentRecord,responseDirectory,"");
        } else if (status.equals("TYPE_ERROR")){
            log.info("go to build: {},{}",formErrorCounter.get(),documentRecord.getRecId());
            return builderDoc(documentRecord,errorDirectory,"type_error");
        } else {
            log.info("go to build: {},{}",xsdErrorCounter.get(),documentRecord.getRecId());
            return builderDoc(documentRecord,errorDirectory,"xsd_error");
        }
    }

    private Mono<Void> builderDoc(DocumentRecord documentRecord, String directory, String outDirectory){
        String fileToSave;
        String outDir;
        LotusDocumentType document = documentRecord.getDocument();
        if (directory.equals(responseDirectory)) {
            outDir = directory + File.separator + document.getForm() + File.separator;
            fileToSave = outDir + document.getForm() + "_" + successCounter.incrementAndGet() + ".xml";
        } else if (outDirectory.equals("type_error")) {
            outDir = directory + File.separator + outDirectory + File.separator + document.getSourceSystemName() + File.separator;
            fileToSave = outDir + document.getSourceSystemName() + "_" + formErrorCounter.incrementAndGet() + ".xml";
        } else {
            outDir = directory + File.separator + outDirectory + File.separator + document.getForm() + File.separator;
            fileToSave = outDir + document.getForm()  + "_" + xsdErrorCounter.incrementAndGet() + ".xml";
        }
        log.info("file to save: {}", fileToSave);
        Path dir = Path.of(outDir);
        Path file = Path.of(fileToSave);
        String xmlContent = marshallFile(documentRecord);
        DataBuffer dataBuffer = new DefaultDataBufferFactory().wrap(xmlContent.getBytes(StandardCharsets.UTF_8));
        return Mono.fromRunnable(() -> {
            try {
                FileUtils.forceMkdir(dir.toFile());
//                Files.createDirectories(Path.of(outDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        })
                .then(DataBufferUtils.write(Flux.just(dataBuffer), file, StandardOpenOption.CREATE))
                .doFinally(signalType -> DataBufferUtils.release(dataBuffer));
    }

    private String marshallFile(DocumentRecord documentRecord){
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(DocumentRecord.class);
            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(documentRecord, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }


    @PreDestroy
    public Mono<Void> deleteAll(){
        try {
            FileSystemUtils.deleteRecursively(Path.of(responseDirectory));
            FileSystemUtils.deleteRecursively(Path.of(errorDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }
}
