package com.ttorang.domain.qna.repository;

import com.ttorang.domain.qna.model.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}