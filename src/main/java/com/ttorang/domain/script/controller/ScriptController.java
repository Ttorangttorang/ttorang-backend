package com.ttorang.domain.script.controller;

import com.ttorang.domain.script.model.dto.request.CreateScriptRequest;
import com.ttorang.domain.script.model.dto.request.UpdateScriptRequest;
import com.ttorang.domain.script.model.dto.response.CreateScriptResponse;
import com.ttorang.domain.script.model.dto.response.DeleteScriptResponse;
import com.ttorang.domain.script.model.dto.response.GetScriptListResponse;
import com.ttorang.domain.script.model.dto.response.UpdateScriptResponse;
import com.ttorang.domain.script.service.ScriptService;
import com.ttorang.global.model.RestApiResponse;
import com.ttorang.resolver.userinfo.UserInfo;
import com.ttorang.resolver.userinfo.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @UserInfo UserInfoDto userInfo,
            @Valid @RequestBody CreateScriptRequest request) {
        return RestApiResponse.success(
                scriptService.createScript(request, userInfo.getUserId()));
    }

    @Operation(
            summary = "스크립트 수정 API",
            description = "저장된 스크립트를 수정합니다."
    )
    @PostMapping("/{scriptId}")
    public RestApiResponse<UpdateScriptResponse> updateScript(
            @PathVariable Long scriptId,
            @UserInfo UserInfoDto userInfo,
            @RequestBody UpdateScriptRequest request) {
        return RestApiResponse.success(
                scriptService.updateScript(request, scriptId, userInfo.getUserId()));
    }

    @Operation(
            summary = "스크립트 삭제 API",
            description = "저장된 스크립트와 함께 구성된 예상질문, 답변을 삭제합니다."
    )
    @DeleteMapping("/{scriptId}")
    public RestApiResponse<DeleteScriptResponse> deleteScript(
            @PathVariable Long scriptId,
            @UserInfo UserInfoDto userInfo) {
        return RestApiResponse.success(
                scriptService.deleteScript(scriptId, userInfo.getUserId()));
    }

    @Operation(
            summary = "스크립트 조회 API",
            description = "내가 저장한 발표대본, 예상질문/답변을 조회합니다"
    )
    @GetMapping("")
    public RestApiResponse<List<GetScriptListResponse>> getScriptList(
            @UserInfo UserInfoDto userInfo) {
        return RestApiResponse.success(
                scriptService.getScriptList(userInfo.getUserId()));
    }

}
