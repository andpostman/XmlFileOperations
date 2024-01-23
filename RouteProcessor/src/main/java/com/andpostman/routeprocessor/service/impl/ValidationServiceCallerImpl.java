package com.andpostman.routeprocessor.service.impl;

import com.andpostman.routeprocessor.exception.XmlErrorHandler;
import com.andpostman.routeprocessor.property.DocumentRecord;
import com.andpostman.routeprocessor.property.LogResponse;
import com.andpostman.routeprocessor.property.LotusDocumentType;
import jakarta.xml.bind.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import reactor.core.publisher.Mono;
import com.andpostman.routeprocessor.service.ValidationServiceCaller;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.util.Arrays;


@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationServiceCallerImpl implements ValidationServiceCaller {

    @Value("${datasource.xsd.path}")
    private String xsdDirectory;

    private final LogResponseServiceImpl logResponseService;
    private final ResultServiceImpl resultService;
    private XmlErrorHandler errorHandler = new XmlErrorHandler();

    private boolean isValid(DocumentRecord message){
        try {
            JAXBContext context = JAXBContext.newInstance(DocumentRecord.class);
            Marshaller marshaller = context.createMarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            StreamSource[] xsdSources = generateStreamSourcesFromXsdPaths();
            Schema schema = schemaFactory.newSchema(xsdSources);
            marshaller.setSchema(schema);
            marshaller.marshal(message, new DefaultHandler());
            return true;
        } catch (JAXBException | SAXException e) {
            errorHandler.error(e);
            return false;
        }
    }

    private StreamSource[] generateStreamSourcesFromXsdPaths()
    {
        final String[] xsdFilesPaths = {
                xsdDirectory + "Documents.xsd",
                xsdDirectory + "LotusDocumentType.xsd"
//                ValidationServiceCallerImpl.class.getClassLoader().getResource("Documents.xsd").getFile(),
//                ValidationServiceCallerImpl.class.getClassLoader().getResource("LotusDocumentType.xsd").getFile()
        };
        return Arrays.stream(xsdFilesPaths)
                .map(StreamSource::new)
                .toList()
                .toArray(new StreamSource[xsdFilesPaths.length]);
    }

    @Override
    public Mono<Void> validateMessage(DocumentRecord documentRecord) {
        if(isValid(documentRecord)){
            LotusDocumentType document = documentRecord.getDocument();
            if (document.getForm().equals("CARD") || document.getForm().equals("PERSON") || document.getForm().equals("CURRENCY_FORM")){
                return resultService.sendValidResult(documentRecord);
            } else {
                logResponseService.printLogResponse(
                        LogResponse.builder()
                                .status(LogResponse.Status.TYPE_ERROR)
                                .unid(document.getUNID())
                                .message(LogResponse.Status.TYPE_ERROR.name())
                                .build()
                );
                return resultService.sendFormTypeErrorResult(documentRecord);
            }
        }else {
            return resultService.sendXsdErrorResult(documentRecord, errorHandler);
        }
    }


}
