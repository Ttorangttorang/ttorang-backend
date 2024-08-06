package com.ttorang.domain.script.model.dto.response;

import com.ttorang.domain.script.model.entity.Script;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Schema(description = "스크립트 리스트 조회 Response Dto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetScriptListResponse {

    @Schema(description = "스크립트 Id", example = "1")
    private Long id;

    @Schema(description = "발표 대본", example = "발표 대본 예시")
    private String content;

    @Schema(description = "발표 주제", example = "발표 주제 예시")
    private String topic;

    @Schema(description = "등록일", example = "2024-08-07 00:02:06.721972")
    private LocalDateTime regTime;


    public static GetScriptListResponse of(
            Long id, String content,
            String topic, LocalDateTime regTime) {
        return new GetScriptListResponse(
                id, content, topic, regTime);
    }
}
