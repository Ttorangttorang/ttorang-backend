package com.ttorang.domain.script.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "스크립트 삭제 Response Dto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteScriptResponse {

    @Schema(description = "스크립트 Id", example = "1")
    private Long id;

    public static DeleteScriptResponse of(Long scriptId) {
        return new DeleteScriptResponse(scriptId);
    }
}
