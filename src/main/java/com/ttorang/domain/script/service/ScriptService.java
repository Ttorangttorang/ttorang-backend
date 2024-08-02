package com.ttorang.domain.script.service;

import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.qna.repository.QnaRepository;
import com.ttorang.domain.script.model.dto.request.CreateScriptRequest;
import com.ttorang.domain.script.model.dto.response.CreateScriptResponse;
import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.script.repository.ScriptRepository;
import com.ttorang.global.code.ErrorCode;
import com.ttorang.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

        scriptRepository.findById(savedScript.getId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXIST_SCRIPT));

        List<Qna> qnas = request.getQnaList().stream()
                .map(qnaRequest -> qnaRequest.toEntity(savedScript)) // qnaRequest를 entity로 변환
                .collect(Collectors.toList());

        qnaRepository.saveAll(qnas);

        return CreateScriptResponse.of(savedScript);
    }
}
