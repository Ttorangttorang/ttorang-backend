package com.ttorang.global.util;

import com.ttorang.global.error.exception.AuthenticationException;
import com.ttorang.jwt.constant.GrantType;
import org.springframework.util.StringUtils;

import static com.ttorang.global.code.ErrorCode.NOT_EXISTS_AUTHORIZATION;
import static com.ttorang.global.code.ErrorCode.NOT_VALID_BEARER_GRANT_TYPE;

public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {

        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(NOT_EXISTS_AUTHORIZATION);
        }

        String[] authorization = authorizationHeader.split(" ");
        if (authorization.length < 2 || (!GrantType.BEARER.getType().equals(authorization[0]))) {
            throw new AuthenticationException(NOT_VALID_BEARER_GRANT_TYPE);
        }

    }

}
