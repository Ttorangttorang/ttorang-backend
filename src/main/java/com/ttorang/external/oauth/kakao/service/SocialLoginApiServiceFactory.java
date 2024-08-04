package com.ttorang.external.oauth.kakao.service;

import com.ttorang.domain.user.constant.UserType;
import com.ttorang.external.service.SocialLoginApiService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialLoginApiService;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiService) {
        this.socialLoginApiService = socialLoginApiService;
    }

    public static SocialLoginApiService getSocialLoginApiService(UserType userType) {
        String socialLoginApiServiceBeanName = "";

        if (UserType.KAKAO.equals(userType)) {
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }

        return socialLoginApiService.get(socialLoginApiServiceBeanName);
    }

}
