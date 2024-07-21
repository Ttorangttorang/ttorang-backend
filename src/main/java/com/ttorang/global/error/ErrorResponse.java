package com.ttorang.global.error;


import com.ttorang.global.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String code;
    private final String message;

    public static ErrorResponse of(String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }

    public static ErrorResponse of(String errorCode, BindingResult bindingResult) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(createErrorMessage(bindingResult)) //bindingResult에 메세지 담아서 클라이언트에 전달
                .build();
    }

    private static String createErrorMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            if (!isFirst) {
                sb.append(", "); // 첫번째가 아니면 콤마를 붙여준다.
            } else {
                isFirst = false; // 첫번째면 isFirst를 false로 바꿔준다.
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("]");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatusCode().getCode())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatusCode().getCode())
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

}
