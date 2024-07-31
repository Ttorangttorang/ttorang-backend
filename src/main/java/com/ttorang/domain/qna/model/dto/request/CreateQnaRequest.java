package com.ttorang.domain.qna.model.dto.request;

import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.script.model.entity.Script;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateQnaRequest {

    private String question;

    private String answer;

    private Long scriptId;

    public Qna toEntity(Script script) {
        return Qna.newQna(question, answer, script);
    }

}
