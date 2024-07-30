package com.ttorang.api.clova.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateClovaRequest {

//    @NotNull(message = "발표 주제는 필수입니다.")
//    @Size(min = 1, max = 100, message = "발표 주제는 1자에서 100자 사이여야 합니다.")
    @Schema(description = "발표 주제", example = "생활 속에서 실천할 수 있는 환경 보호 방안")
    private String topic;

//    @NotNull(message = "발표 목적은 필수입니다.")
    @Schema(description = "발표 목적", example = "회사 컨퍼런스")
    private String purpose;

//    @NotNull(message = "종결 어미는 필수입니다.")
    @Schema(description = "종결 어미", example = "~ 합니다체")
    private String word;

//    @NotNull(message = "발표 내용은 필수입니다.")
//    @Size(min = 1, max = 3000, message = "발표 내용은 1자에서 3000자 사이여야 합니다.")
    @Schema(description = "발표 내용", example = "발표 내용 예제")
    private String content;

    @Schema(description = "중복 표현 제거", example = "Y")
    private String duplicate;

}
