package com.ttorang.domain.qna.model.entity;

import com.ttorang.domain.script.model.entity.Script;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Qna {

    @Id
    @Column(name = "qna_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private Script script;

    private String question;

    private String answer;

    public static Qna newQna(String question, String answer, Script script) {
        return Qna.builder()
                .question(question)
                .answer(answer)
                .script(script)
                .build();

    }
}
