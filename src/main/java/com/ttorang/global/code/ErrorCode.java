package com.ttorang.global.code;

import lombok.Getter;

import static com.ttorang.global.code.HttpStatusCode.*;

@Getter
public enum ErrorCode {

    TEST(INTERNAL_SERVER_ERROR, "T001", "business exception test"),

    /**
     * 404 Not Found
     */
    E404_NOT_EXIST_SCRIPT(NOT_FOUND, "NF001", "존재하지 않는 스크립트입니다."),

    /**
     * 회원
     */
    INVALID_USER_TYPE(BAD_REQUEST, "M001", "잘못된 회원 타입입니다."),
    ALREADY_REGISTERED_USER(BAD_REQUEST, "M002", "이미 가입된 회원입니다."),

    /**
     * 인증 && 인가
     */
    TOKEN_EXPIRED(UNAUTHORIZED, "A001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(UNAUTHORIZED,  "A002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(UNAUTHORIZED,"A003" ,"Authorization Header가 빈값입니다." ),
    NOT_VALID_BEARER_GRANT_TYPE(UNAUTHORIZED,"A004", "인증 타입이 Bearer 타입이 아닙니다." ),
    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED,"A005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED,"A006", "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(UNAUTHORIZED, "A007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN(FORBIDDEN, "A-008", "관리자 Role이 아닙니다.");


    private final HttpStatusCode statusCode;
    private final String code;
    private final String message;

    ErrorCode(HttpStatusCode statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }
}
