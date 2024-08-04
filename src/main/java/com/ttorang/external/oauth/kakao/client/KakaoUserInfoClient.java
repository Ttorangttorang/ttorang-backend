package com.ttorang.external.oauth.kakao.client;

import com.ttorang.external.oauth.kakao.dto.GetKakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoUserInfoClient")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    GetKakaoUserInfoResponse getKakaoUserInfo(@RequestHeader("Content-Type") String contentType,
                                              @RequestHeader("Authorization") String accessToken);


}
