package com.ttorang.domain.script.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateScriptRequest {

    @Schema(description = "스크립트 내용", example = "스크립트 내용 예시")
    private String content;
}
