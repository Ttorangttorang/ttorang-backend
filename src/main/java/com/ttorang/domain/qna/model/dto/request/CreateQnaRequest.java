package com.ttorang.domain.qna.model.dto.request;

import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.script.model.entity.Script;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateQnaRequest {

    @Schema(description = "예상 질문", example = "예상 질문 예시")
    private String question;

    @Schema(description = "예상 답변", example = "예상 답변 예시")
    private String answer;

    public Qna toEntity(Script script) {
        return Qna.newQna(question, answer, script);
    }

}
