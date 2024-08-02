package com.ttorang.api.clova.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ttorang.api.clova.model.dto.request.CreateClovaForQnARequest;
import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Slf4j
@Service
@Transactional
public class ClovaChatForQnAServiceImpl implements ClovaChatForQnaService {

    @Value("${clovastudio.second.api.key}")
    private String clovastudioApiKey;
    @Value("${clovastudio.second.gateway.api.key}")
    private String gateWayApiKey;
    @Value("${clovastudio.second.request.id}")
    private String clovastudioRequestId;

    private final String CLOVA_API_URL = "https://clovastudio.stream.ntruss.com/serviceapp/v1/chat-completions/HCX-003";
    private final String CLOVA_API_URL_TEST = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions/HCX-003";
    private final String CLOVA_API_KEY_HEADER = "X-NCP-CLOVASTUDIO-API-KEY";
    private final String GATEWAY_API_KEY_HEADER = "X-NCP-APIGW-API-KEY";
    private final String CLOVA_STUDIO_REQUEST_ID = "X-NCP-CLOVASTUDIO-REQUEST-ID";

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ClovaChatForQnAServiceImpl(WebClient webClient) {
        this.webClient = webClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Flux<String> requestClovaForQna(CreateClovaForQnARequest request) {

        String requestJson = requestJson(request);

        return webClient.post()
                .uri(CLOVA_API_URL_TEST)
                .header(CLOVA_API_KEY_HEADER, clovastudioApiKey)
                .header(GATEWAY_API_KEY_HEADER, gateWayApiKey)
                .header(CLOVA_STUDIO_REQUEST_ID, clovastudioRequestId)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CACHE_CONTROL, "no-cache")
                .header(HttpHeaders.CONNECTION, "keep-alive")
                .bodyValue(requestJson)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> {
                            log.error("Error response status: {}", response.statusCode());
                            return Mono.error(new RuntimeException("Error response status: " + response.statusCode()));
                        }
                )
                .bodyToFlux(String.class)
                .takeUntil(data -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(data);
                        String stopReason = jsonNode.path("stopReason").asText();
                        return "stop_before".equals(stopReason);
                    } catch (JsonProcessingException e) {
                        log.error("Error processing JSON", e);
                        return false;
                    }
                })
                // TODO : 에러 처리
                .doOnError(WebClientResponseException.class, e -> {
                    log.error("WebClientResponseException: {}", e.getMessage());
                })
                .doOnError(ConnectException.class, e -> {
                    log.error("ConnectException: {}", e.getMessage());
                })
                .doOnError(SocketTimeoutException.class, e -> {
                    log.error("SocketTimeoutException: {}", e.getMessage());
                })
                .doOnError(e -> {
                    log.error("Unexpected error: {}", e.getMessage());
                });
    }

    /*user*/
    @Override
    public String requestUserMessageForQna(CreateClovaForQnARequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n- 발표 내용: ").append(request.getContent().replace("\n", "\\n"));
        return sb.toString();
    }

    /*system*/
    @Override
    public String requestJson(CreateClovaForQnARequest request) {

        String content = String.format(
                "- 당신은 주어진 발표 대본으로 예상가능한 질문,답변을 만들어주는 어시스턴트 입니다.\\r\\n" +
                "- 다음 지침을 엄격히 준수하여 업무를 수행합니다.\\r\\n###엄격한 준수 사항\\r\\n" +
                "- 생략을 절대 금지합니다.\\r\\n" +
                "- 사용자가 요청한 발표대본으로 예상가능한 질문,답변을 4개씩 만들어 줍니다.\\r\\n" +
                "- 사용자가 읽기 편하게 가독성 있게 정리합니다.\\r\\n" +
                "- 질문,답변은 최대 150자를 넘지 않습니다.\\r\\n" +
                "###끝\\r\\n\\r\\n" +
                "###형식\\r\\n" +
                "Q1.\\r\\n" +
                "A1. \\r\\n" +
                "Q2.\\r\\n" +
                "A2. \\r\\n" +
                "###끝\\r\\n\\n");

        String userMessage = requestUserMessageForQna(request);

        ObjectNode requestJson = objectMapper.createObjectNode();
        ArrayNode messagesArray = objectMapper.createArrayNode();

        ObjectNode systemMessage = objectMapper.createObjectNode();
        systemMessage.put("role", "system");
        systemMessage.put("content", content);
        messagesArray.add(systemMessage);

        ObjectNode userMessageNode = objectMapper.createObjectNode();
        userMessageNode.put("role", "user");
        userMessageNode.put("content", userMessage);
        messagesArray.add(userMessageNode);

        requestJson.set("messages", messagesArray);
        requestJson.put("topP", 0.8);
        requestJson.put("topK", 0);
        requestJson.put("maxTokens", 350);
        requestJson.put("temperature", 0.5);
        requestJson.put("repeatPenalty", 10.0);
        requestJson.put("includeAiFilters", false);
        requestJson.put("seed", 0);

        try {
            return objectMapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON Processing Exception", e);
        }
    }
}
