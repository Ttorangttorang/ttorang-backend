package com.ttorang.api.clova.controller;

import com.ttorang.api.clova.model.dto.request.CreateClovaRequest;
import com.ttorang.api.clova.model.dto.response.CreateClovaResponse;
import com.ttorang.api.clova.service.ClovaChatService;
import com.ttorang.global.model.RestApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clova")
public class ClovaController {

    private final ClovaChatService clovaChatService;

    @GetMapping(value = "/script", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> requestClova(@RequestBody CreateClovaRequest request) {
        return clovaChatService.requestClova(request);
    }

    @GetMapping(value = "/script2")
    public RestApiResponse<String> requestClova2(@RequestBody CreateClovaRequest request) {
        return RestApiResponse.success(clovaChatService.excuteClova(request));
    }


}
