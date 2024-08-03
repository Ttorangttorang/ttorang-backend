package com.ttorang.domain.script.repository;

import com.ttorang.domain.script.model.entity.Script;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptRepository extends JpaRepository<Script, Long> {
}
