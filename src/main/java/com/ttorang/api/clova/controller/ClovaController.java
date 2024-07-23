package com.ttorang.api.clova.controller;

import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import com.ttorang.api.clova.service.ClovaChatService;
import com.ttorang.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clova")
@Tag(name = "clova", description = "클로바 API")
public class ClovaController {

    private final ClovaChatService clovaChatService;

    @Operation(
            summary = "클로바 API (테스트)",
            description = "발표 내용 입력 후 CLOVA STUDIO를 통해 교정된 발표 내용을 반환."
    )
    @PostMapping(value = "/script", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> requestClova(@RequestBody CreateClovaRequest request) {
        return clovaChatService.requestClova(request);
    }

    @Operation(
            summary = "클로바 API",
            description = "발표 내용 입력 후 CLOVA STUDIO를 통해 교정된 발표 내용을 반환."
    )
    @PostMapping(value = "/script2")
    public RestApiResponse<String> requestClova2(@RequestBody CreateClovaRequest request) {
        return RestApiResponse.success(clovaChatService.excuteClova(request));
    }


}