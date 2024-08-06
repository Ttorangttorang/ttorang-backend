package com.ttorang.domain.script.service;

import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.qna.repository.QnaRepository;
import com.ttorang.domain.script.model.dto.request.CreateScriptRequest;
import com.ttorang.domain.script.model.dto.request.UpdateScriptRequest;
import com.ttorang.domain.script.model.dto.response.CreateScriptResponse;
import com.ttorang.domain.script.model.dto.response.DeleteScriptResponse;
import com.ttorang.domain.script.model.dto.response.UpdateScriptResponse;
import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.script.repository.ScriptRepository;
import com.ttorang.domain.user.model.entity.User;
import com.ttorang.domain.user.repository.UserRepository;
import com.ttorang.global.error.exception.ForbiddenException;
import com.ttorang.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.ttorang.global.code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository scriptRepository;
    private final QnaRepository qnaRepository;
    private final UserRepository userRepository;

    /**
     * 또랑이가 교정해준 발표문 저장
     */
    public CreateScriptResponse createScript(CreateScriptRequest request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_EXISTS_AUTHORIZATION));

        Script script = Script.builder()
                .content(request.getContent())
                .topic(request.getTopic())
                .user(user)
                .build();

        Script savedScript = scriptRepository.save(script);

        scriptRepository.findById(savedScript.getId())
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXIST_SCRIPT));

        List<Qna> qnas = request.getQnaList().stream()
                .map(qnaRequest -> qnaRequest.toEntity(savedScript)) // qnaRequest를 entity로 변환
                .collect(Collectors.toList());

        qnaRepository.saveAll(qnas);

        return CreateScriptResponse.of(savedScript);
    }

    /**
     * 발표문 수정
     */
    public UpdateScriptResponse updateScript(UpdateScriptRequest request, Long scriptId, Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(NOT_EXISTS_AUTHORIZATION));

        Script script = scriptRepository.findById(scriptId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXIST_SCRIPT));

        if (!script.getUser().getId().equals(userId)) {
            throw new ForbiddenException(E403_NOT_MY_SCRIPT);
        }

        script.updateScript(request.getContent());

        return UpdateScriptResponse.of(script);
    }

    /**
     * 스크립트 삭제
     */
    public DeleteScriptResponse deleteScript(Long scriptId, Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(NOT_EXISTS_AUTHORIZATION));

        Script script = scriptRepository.findById(scriptId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXIST_SCRIPT));

        if (!script.getUser().getId().equals(userId)) {
            throw new ForbiddenException(E403_NOT_MY_SCRIPT);
        }

        scriptRepository.deleteById(scriptId);

        return DeleteScriptResponse.of(scriptId);
    }

}
