package com.ttorang.domain.qna.repository;

import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.script.model.entity.Script;
import com.ttorang.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByScriptId(Long scriptId);

    void deleteByScriptIn(List<Script> scriptsToDelete);
}
