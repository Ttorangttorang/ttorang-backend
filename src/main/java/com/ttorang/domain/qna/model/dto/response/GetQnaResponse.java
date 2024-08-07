package com.ttorang.domain.qna.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetQnaResponse {

    @Schema(description = "예상 질문", example = "예상 질문 예시")
    private String question;

    @Schema(description = "예상 답변", example = "예상 답변 예시")
    private String answer;

    public static GetQnaResponse of(String question, String answer) {
        return new GetQnaResponse(question, answer);
    }
}
