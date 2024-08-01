package com.ttorang.domain.script.model.dto.request;

import com.ttorang.domain.qna.model.dto.request.CreateQnaRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateScriptRequest {

    //TODO: validation 추가

    private String topic;

    private String purpose;

    private String word;

    private String content;

    private List<CreateQnaRequest> qnaList;

}
