package com.ttorang.global.error.exception;


import com.ttorang.global.code.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }


}
