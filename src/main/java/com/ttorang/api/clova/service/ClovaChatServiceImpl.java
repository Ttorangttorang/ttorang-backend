package com.ttorang.api.clova.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClovaChatServiceImpl {

    @Value("${clovastudio.api.key}")
    private String clovastudioApiKey;
    @Value("${clovastudio.gateway.api.key}")
    private String gateWayApiKey;
    @Value("${clovastudio.request.id}")
    private String clovastudioRequestId;

    private final String CLOVA_API_URL = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-003";
    private final String CLOVA_API_KEY_HEADER = "X-NCP-CLOVASTUDIO-API-KEY";
    private final String GATEWAY_API_KEY_HEADER = "X-NCP-APIGW-API-KEY";
    private final String CLOVA_STUDIO_REQUEST_ID = "X-NCP-CLOVASTUDIO-REQUEST-ID";

    private final WebClient webClient;

    public ClovaChatServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }













}
