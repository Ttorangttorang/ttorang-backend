package com.ttorang.global.error.exception;


import com.ttorang.global.code.ErrorCode;

public class ForbiddenException extends CustomRunTimeException {

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

    public ForbiddenException(String message) {
        super(message, ErrorCode.E403_FORBIDDEN);
    }
}
