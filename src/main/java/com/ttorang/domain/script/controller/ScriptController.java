package com.ttorang.domain.script.controller;

import com.ttorang.domain.script.model.dto.request.CreateScriptRequest;
import com.ttorang.domain.script.model.dto.response.CreateScriptResponse;
import com.ttorang.domain.script.service.ScriptService;
import com.ttorang.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/script")
@Tag(name = "script", description = "스크립트 API")
public class ScriptController {

    private final ScriptService scriptService;

    @Operation(
            summary = "스크립트 저장 API",
            description = "step3 이후 또랑이가 교정한 스크립트를 저장합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateScriptResponse> createScript(
            @RequestBody CreateScriptRequest request) {

        return RestApiResponse.success(scriptService.createScript(request));
    }





}