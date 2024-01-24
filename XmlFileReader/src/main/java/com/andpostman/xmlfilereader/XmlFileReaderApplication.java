package com.andpostman.xmlfilereader;

import com.andpostman.xmlfilereader.file.FileXmlReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class XmlFileReaderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(XmlFileReaderApplication.class, args);
        FileXmlReader reader = context.getBean(FileXmlReader.class);
        reader.readXmlFiles().subscribe();
        context.close();
    }

}
