package com.ttorang.api.clova.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateClovaRequest {

    // TODO : validation 추가

    @Schema(description = "발표 주제", example = "생활 속에서 실천할 수 있는 환경 보호 방안")
    private String topic;

    @Schema(description = "발표 목적", example = "회사 컨퍼런스")
    private String purpose;

    @Schema(description = "종결 어미", example = "~ 합니다체")
    private String word;

    @Schema(description = "발표 내용", example = "발표 내용 예제")
    private String content;

//    @Schema(description = "중복 표현 제거", example = "Y")
//    private String duplicate;

}
