package com.ttorang.api.clova.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateClovaForQnARequest {

    @NotNull(message = "교정된 발표 내용은 필수입니다.")
    @Size(min = 1, max = 3000, message = "발표 내용은 1자에서 3000자 사이여야 합니다.")
    @Schema(description = "교정된 발표 내용", example = "교정된 발표 내용 예제")
    private String content;

}
