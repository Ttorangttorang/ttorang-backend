package com.ttorang.domain.script.controller;

import com.ttorang.domain.script.model.dto.request.CreateScriptRequest;
import com.ttorang.domain.script.model.dto.request.UpdateScriptRequest;
import com.ttorang.domain.script.model.dto.response.CreateScriptResponse;
import com.ttorang.domain.script.model.dto.response.DeleteScriptResponse;
import com.ttorang.domain.script.model.dto.response.UpdateScriptResponse;
import com.ttorang.domain.script.service.ScriptService;
import com.ttorang.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
            @Valid @RequestBody CreateScriptRequest request) {
        return RestApiResponse.success(scriptService.createScript(request));
    }

    @Operation(
            summary = "스크립트 수정 API",
            description = "저장된 스크립트를 수정합니다."
    )
    @PostMapping("/{scriptId}")
    public RestApiResponse<UpdateScriptResponse> updateScript(
            @PathVariable Long scriptId,
            @RequestBody UpdateScriptRequest request) {
        return RestApiResponse.success(scriptService.updateScript(request, scriptId));
    }

    @Operation(
            summary = "스크립트 삭제 API",
            description = "저장된 스크립트와 함께 구성된 예상질문, 답변을 삭제합니다."
    )
    @DeleteMapping("/{scriptId}")
    public RestApiResponse<DeleteScriptResponse> deleteScript(
            @PathVariable Long scriptId) {
        return RestApiResponse.success(scriptService.deleteScript(scriptId));
    }

}
