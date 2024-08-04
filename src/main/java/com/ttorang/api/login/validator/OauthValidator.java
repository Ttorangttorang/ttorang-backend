package com.ttorang.api.login.validator;

import com.ttorang.domain.user.constant.UserType;
import com.ttorang.global.code.ErrorCode;
import com.ttorang.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class OauthValidator {

    public void validateUserType(String userType) {
        if (!UserType.isUserType(userType)) {
            throw new BusinessException(ErrorCode.INVALID_USER_TYPE);
        }
    }


}
