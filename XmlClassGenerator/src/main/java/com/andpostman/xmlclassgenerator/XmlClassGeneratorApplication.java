package com.andpostman.xmlclassgenerator;

import com.andpostman.xmlclassgenerator.service.DocumentsGeneratorServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class XmlClassGeneratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(XmlClassGeneratorApplication.class, args);
        DocumentsGeneratorServiceImpl reader = context.getBean(DocumentsGeneratorServiceImpl.class);
        reader.generateDocuments().subscribe();
        context.close();
    }

}
