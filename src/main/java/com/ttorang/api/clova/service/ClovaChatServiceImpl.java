package com.ttorang.api.clova.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import com.ttorang.api.clova.model.dto.response.CreateClovaResponse;
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
public class ClovaChatServiceImpl implements ClovaChatService {

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

    @Override
    public CreateClovaResponse requestClova2(CreateClovaRequest request) {
        log.info("여기 잘 찍히는가?");
        String requestJson = requestJson(request);
        ObjectMapper objectMapper = new ObjectMapper();

        String responseJson = webClient.post()
                .uri(CLOVA_API_URL)
                .header(CLOVA_API_KEY_HEADER, clovastudioApiKey)
                .header(GATEWAY_API_KEY_HEADER, gateWayApiKey)
                .header(CLOVA_STUDIO_REQUEST_ID, clovastudioRequestId)
                .accept(MediaType.APPLICATION_JSON)
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
                .bodyToMono(String.class)
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
                })
                .block(); // Mono를 블록하여 동기식으로 반환

        try {
            return objectMapper.readValue(responseJson, CreateClovaResponse.class);
        } catch (Exception e) {
            log.error("Error parsing JSON response", e);
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

    @Override
    public Flux<String> requestClova(CreateClovaRequest request) {

        String requestJson = requestJson(request);

        return webClient.post()
                .uri(CLOVA_API_URL)
                .header(CLOVA_API_KEY_HEADER, clovastudioApiKey)
                .header(GATEWAY_API_KEY_HEADER, gateWayApiKey)
                .header(CLOVA_STUDIO_REQUEST_ID, clovastudioRequestId)
//                .header(HttpHeaders.ACCEPT_CHARSET, "UTF-8")
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
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode jsonNode = mapper.readTree(data);
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
    public String requestUserMessage(CreateClovaRequest request) {
        StringBuilder sb = new StringBuilder();

        sb.append("- 발표 주제: " + request.getTopic());
        sb.append("\n- 발표 목적: " + request.getPurpose());
        sb.append("\n- 종결 어미: " + request.getWord());
        sb.append("\n- 중복 문항 제거: " + request.getDuplicate());
        sb.append("\n- 발표 내용: " + request.getContent());

        return sb.toString();
    }

    /*system*/
    @Override
    public String requestJson(CreateClovaRequest request) {

//        String content = String.format("- 너는 발표 내용을 발표 대본으로 매끄럽게 만들어주는 논술 선생님이야.\\r\\n\\r\\n" +
//                "- 사용자가 요청한 주제에 맞게 발표 대본을 논리적이고 일관성 있게 전개해.\\r\\n" +
//                "- 사용자가 요청하는 키워드는 발표주제, 발표목적, 종결어미 가 있는데 각각 상황에 맞게 발표대본을 준비해.\\r\\n" +
//                "- 사용자가 요청한 발표 목적에 맞게 문장을 명확하고 간결하게 구성해.\\r\\n" +
//                "- 사용자가 요청한 종결어미에 맞게 정리해.\\r\\n\\r\\n" +
//                "- 답변해줄 문장으로는 발표 대본 내용으로만 구성해.");

        String content = String.format("- 당신은 텍스트를 발표 대본으로 바꿔주는 어시스턴트 입니다.\\r\\n\\r\\n" +
                "- 주어진 텍스트를 분석하고 풍부하고 명확한 발표 대본으로 제공합니다.\\r\\n" +
                "- 다음 지침을 엄격히 준수하여 업무를 수행합니다.\\r\\n\\r\\n" +
                "###엄격한 준수 사항\\r\\n\\r\\n" +
                "- 사용자가 요청한 발표주제, 발표목적, 종결어미를 파악해서 발표대본을 완성합니다.\\r\\n" +
                "- 중복표현 제거가 '\\'Y'\\' 일 경우 중복표현을 제거합니다.\\r\\n" +
                "- 사용자가 읽기 편하게 가독성 있게 정리합니다. 문단마다 개행을 사용합니다.\\r\\n" +
                "- 발표 대본을 개선 후 개선된 내용을 30자 미만으로 정리 합니다.\\r\\n" +
                "- 마지막으로 개선된 발표내용에 대해 예상 질문과 답변을 3개씩 구성합니다.\\r\\n" +
                "###끝\\r\\n\\r\\n" +
                "###응답 형식\\r\\n1. 발표 대본\\r\\n2. 개선 내용\\r\\n3. 예상 질문,답변\\r\\n###끝");

        String userMessage = requestUserMessage(request);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestJson = mapper.createObjectNode();
        ObjectNode message = mapper.createObjectNode();
        message.put("role", "system");
        message.put("content", content);

        message.put("role", "user");
        message.put("content", userMessage);

        requestJson.set("messages", mapper.createArrayNode().add(message));
        requestJson.put("topP", 0.8);
        requestJson.put("topK", 0);
        requestJson.put("maxTokens", 2000);
        requestJson.put("temperature", 0.5);
        requestJson.put("repeatPenalty", 10.0);
        requestJson.put("stopBefore", mapper.createArrayNode());
        requestJson.put("includeAiFilters", false);
        requestJson.put("seed", 0);

        try {
            return mapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON Processing Exception", e);
        }
    }
}
