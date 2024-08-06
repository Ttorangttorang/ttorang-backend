package com.ttorang.domain.script.model.dto.request;

import com.ttorang.domain.qna.model.dto.request.CreateQnaRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateScriptRequest {

    @Schema(description = "발표 대본", example = "발표 대본 예시")
    @NotNull(message = "저장할 발표내용은 필수 입니다.")
    private String content;

    @Schema(description = "발표 주제", example = "발표 주제 예시")
    private String topic;

    private List<CreateQnaRequest> qnaList;

}
