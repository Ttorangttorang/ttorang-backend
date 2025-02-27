package com.ttorang.domain.script.model.dto.response;

import com.ttorang.domain.script.model.entity.Script;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "스크립트 저장 Response Dto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateScriptResponse {

    @Schema(description = "스크립트 Id", example = "1")
    private Long id;

    public static CreateScriptResponse of(Script script) {
        return new CreateScriptResponse(
                script.getId()
        );
    }
}
