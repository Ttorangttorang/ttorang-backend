package com.ttorang.external.oauth.kakao.service;

import com.ttorang.domain.user.constant.UserType;
import com.ttorang.external.oauth.kakao.client.KakaoUserInfoClient;
import com.ttorang.external.oauth.kakao.dto.GetKakaoUserInfoResponse;
import com.ttorang.external.oauth.model.OAuthAttributes;
import com.ttorang.external.service.SocialLoginApiService;
import com.ttorang.jwt.constant.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        GetKakaoUserInfoResponse getKakaoUserInfoResponse =
                kakaoUserInfoClient.getKakaoUserInfo(
                        CONTENT_TYPE, GrantType.BEARER.getType() + " " + accessToken);

        GetKakaoUserInfoResponse.KakaoAccount kakaoAccount = getKakaoUserInfoResponse.getKakaoAccount();
        String email = kakaoAccount.getEmail();

        return OAuthAttributes.builder()
                .email(!StringUtils.hasText(email) ? getKakaoUserInfoResponse.getId() : email)
                .name(kakaoAccount.getProfile().getNickname())
                .profile(null) //FIXME: 필요하면 사용
                .userType(UserType.KAKAO)
                .build();
    }
}
