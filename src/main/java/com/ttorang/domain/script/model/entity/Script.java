package com.ttorang.domain.script.model.entity;

import com.ttorang.common.entity.BaseTimeEntity;
import com.ttorang.domain.qna.model.entity.Qna;
import com.ttorang.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Script extends BaseTimeEntity {

    @Id
    @Column(name = "script_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "script", cascade = CascadeType.REMOVE)
    private List<Qna> qna = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private String topic;

    private String purpose;

    private String word;

    private String duplicate;

    public void updateScript(String content) {
        this.content = content;
    }
}
