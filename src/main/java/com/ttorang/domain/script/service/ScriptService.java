package com.ttorang.domain.script.service;

import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.qna.repository.QnaRepository;
import com.ttorang.domain.script.model.dto.request.CreateScriptRequest;
import com.ttorang.domain.script.model.dto.response.CreateScriptResponse;
import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.script.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository scriptRepository;
    private final QnaRepository qnaRepository;

    /**
     * 또랑이가 교정해준 발표문 저장
     */
    public CreateScriptResponse createScript(CreateScriptRequest request) {

        Script script = Script.builder()
                .topic(request.getTopic())
                .purpose(request.getPurpose())
                .word(request.getWord())
                .content(request.getContent())
                .build();

        Script savedScript = scriptRepository.save(script);

        List<Qna> qnas = request.getQnaList().stream()
                .map(qnaRequest -> qnaRequest.toEntity(savedScript))
                .collect(Collectors.toList());

        qnaRepository.saveAll(qnas);

        return CreateScriptResponse.of(savedScript);
    }
}
