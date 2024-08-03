package com.ttorang.api.clova.service;

import com.ttorang.api.clova.model.dto.request.CreateClovaForQnARequest;
import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import reactor.core.publisher.Flux;

public interface ClovaChatForQnaService {
    Flux<String> requestClovaForQna(CreateClovaForQnARequest request);

    String requestJson(CreateClovaForQnARequest request);

    String requestUserMessageForQna(CreateClovaForQnARequest request);
}
