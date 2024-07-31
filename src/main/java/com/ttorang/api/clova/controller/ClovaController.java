package com.ttorang.api.clova.controller;

import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import com.ttorang.api.clova.model.dto.response.CreateClovaResponse;
import com.ttorang.api.clova.service.ClovaChatService;
import com.ttorang.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clova")
@Tag(name = "clova", description = "클로바 API")
public class ClovaController {

    private final ClovaChatService clovaChatService;

    @Operation(
            summary = "발표 내용 교정 API (Flux)",
            description = "발표 내용 입력 후 CLOVA STUDIO를 통해 교정된 발표 내용을 반환"
    )
    @PostMapping(value = "/script", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> requestClova(
            @RequestBody @Valid CreateClovaRequest request) {
        return clovaChatService.requestClova(request);
    }

    // TODO : 스크립트 교정, 예상질문/답변 api 2개로 나누기



}
