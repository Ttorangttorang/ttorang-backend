package com.ttorang.domain.qna.repository;

import com.ttorang.domain.qna.model.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByScriptId(Long scriptId);
}
