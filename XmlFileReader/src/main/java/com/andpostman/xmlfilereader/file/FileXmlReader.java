package com.andpostman.xmlfilereader.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileXmlReader {

    private final FileXmlParser parser;

    @Value("${datasource.xml.read.path}")
    private String directory;

    public Flux<Void> readXmlFiles(){
        Path directoryPath = Paths.get(directory);
        if (Files.isDirectory(directoryPath)) {
            try {
                return Flux.fromStream(
                        Files.walk(directoryPath)
                                .filter(Files::isRegularFile)
                                .filter(file -> file.toString().endsWith(".xml"))
                        ).flatMap(file -> parser.parseFileToSend(file.toFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Flux.empty();
    }
}
