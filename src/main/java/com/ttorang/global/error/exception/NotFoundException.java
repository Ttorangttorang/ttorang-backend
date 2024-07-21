package com.ttorang.global.error.exception;


import com.ttorang.global.code.ErrorCode;

/**
 * 강제로 404 반환할때 사용.
 */
public class NotFoundException extends CustomRunTimeException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

}
