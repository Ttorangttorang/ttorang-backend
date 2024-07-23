package com.ttorang.api.clova.service;

import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import reactor.core.publisher.Flux;

public interface ClovaChatService {
    Flux<String> requestClova(CreateClovaRequest request);
    String requestClova2(CreateClovaRequest request);

    String excuteClova(CreateClovaRequest request);

    String requestJson(CreateClovaRequest request);

    String requestUserMessage(CreateClovaRequest request);
}
