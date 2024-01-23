package com.andpostman.filegenerator;

import com.andpostman.filegenerator.service.FileXmlGeneratorServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FileGeneratorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FileGeneratorApplication.class, args);
        FileXmlGeneratorServiceImpl generatorService = context.getBean(FileXmlGeneratorServiceImpl.class);
        generatorService.generate();
        context.close();
    }

}
