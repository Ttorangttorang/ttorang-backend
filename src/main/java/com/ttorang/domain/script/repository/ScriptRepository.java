package com.ttorang.domain.script.repository;

import com.ttorang.domain.script.model.entity.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByUserId(Long userId);

    Script findByIdAndUserId(Long scriptId, Long userId);

}
