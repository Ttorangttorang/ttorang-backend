package com.ttorang.api.clova.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClovaResponse {

    private Message message;

    @Setter
    @Getter
    public static class Message {

        @Schema(description = "role", example = "assistant")
        private String role;

        @Schema(description = "교정 내용", example = "발표 내용 예제")
        private String content;
    }

//    public CreateClovaResponse of(CreateClovaResponse createClovaResponse) {
//        return new CreateClovaResponse(
//                createClovaResponse.getMessage().getRole(),
//                createClovaResponse.getMessage().getContent());
//    }
}
