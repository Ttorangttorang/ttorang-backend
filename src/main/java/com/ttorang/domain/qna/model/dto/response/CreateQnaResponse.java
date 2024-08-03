package com.ttorang.domain.qna.model.dto.response;

import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateQnaResponse {

    private Long id;

    private String question;

    private String answer;

}
