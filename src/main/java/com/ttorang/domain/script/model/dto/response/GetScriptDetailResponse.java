package com.ttorang.domain.script.model.dto.response;

import com.ttorang.domain.qna.model.dto.response.GetQnaResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "스크립트 상세 조회 Response Dto")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetScriptDetailResponse {

    @Schema(description = "스크립트 Id", example = "1")
    private Long id;

    @Schema(description = "발표 대본", example = "발표 대본 예시")
    private String content;

    private List<GetQnaResponse> qnaList;

    public static GetScriptDetailResponse of(
            Long id, String content, List<GetQnaResponse> qnaList) {
        return new GetScriptDetailResponse(id, content, qnaList);
    }
}
