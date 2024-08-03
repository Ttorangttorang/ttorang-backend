package com.ttorang.domain.script.model.dto.response;

import com.ttorang.domain.qna.model.dto.response.CreateQnaResponse;
import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
//@AllArgsConstructor
public class CreateScriptResponse {

    private Long id;

    private User user;

    private String topic;

    private String purpose;

    private String word;

    private String content;

    private List<CreateQnaResponse> qnaList;

    public CreateScriptResponse(Long id, User user, String topic, String purpose, String word, String content) {
        this.id = id;
        this.user = user;
        this.topic = topic;
        this.purpose = purpose;
        this.word = word;
        this.content = content;
    }

    public static CreateScriptResponse of(Script script) {
        return new CreateScriptResponse(
                script.getId(),
                script.getUser(),
                script.getTopic(),
                script.getPurpose(),
                script.getWord(),
                script.getContent()
        );
    }
}
