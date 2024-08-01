package com.ttorang.global.code;

import lombok.Getter;

import static com.ttorang.global.code.HttpStatusCode.INTERNAL_SERVER_ERROR;
import static com.ttorang.global.code.HttpStatusCode.NOT_FOUND;

@Getter
public enum ErrorCode {

    TEST(INTERNAL_SERVER_ERROR, "T001", "business exception test"),

    /**
     * 404 Not Found
     */
    E404_NOT_EXIST_SCRIPT(NOT_FOUND, "NF001", "존재하지 않는 스크립트입니다.");


    private final HttpStatusCode statusCode;
    private final String code;
    private final String message;

    ErrorCode(HttpStatusCode statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }
}
