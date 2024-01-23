package com.andpostman.documenthandler.service;

import com.andpostman.documenthandler.property.DocumentRecord;
import reactor.core.publisher.Mono;

public interface FileRecorderService {
    Mono<Void> saveFile(String status, DocumentRecord documentRecord);
}
