package com.ttorang.global.code;

import lombok.Getter;

import static com.ttorang.global.code.HttpStatusCode.INTERNAL_SERVER_ERROR;

@Getter
public enum ErrorCode {

    TEST(INTERNAL_SERVER_ERROR, false, "T001", "business exception test");

    private final HttpStatusCode statusCode;
    private final boolean notification;
    private final String code;
    private final String message;

    ErrorCode(HttpStatusCode statusCode, boolean notification, String code, String message) {
        this.statusCode = statusCode;
        this.notification = notification;
        this.code = code;
        this.message = message;
    }
}
