package com.ttorang.web.kakaotoken.controller;

import com.ttorang.global.error.exception.Custom404Exception;
import com.ttorang.global.util.EnvironmentUtil;
import com.ttorang.web.kakaotoken.client.KakaoTokenClient;
import com.ttorang.web.kakaotoken.dto.KakaoTokenDto;
import com.ttorang.web.kakaotoken.util.KakaoApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final EnvironmentUtil env;
    private final KakaoTokenClient kakaoTokenClient;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @GetMapping("/kakao/login")
    public String kakaoLogin2() {
        return "kakaoLoginForm";
    }

    //Step 1: 인가 코드 받기
    @GetMapping("/kakao")
    public void kakaoLogin(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String clientId = env.getKakaoClientId();

        // 결과를 처리할 URI 정보
//        String redirectUri = "http://localhost:8080/oauth/kakao/callback";
        String redirectUri = "https://ttorang.site/oauth/kakao/callback";

        // 카카오 accessToken 발급 요청
        // 해당 서비스 URL이 유효한 URL인지 확인
        String kakaoCallURL = KakaoApiUtil.getKakaoAuthorizeURL(clientId, redirectUri);

        log.info("[-] kakaoLogin Kakao First CallURL : {}", kakaoCallURL);

        try {
            response.sendRedirect(kakaoCallURL);
        } catch (IOException e) {
            log.error("[-] kakaoLogin redirect error ", e);
            throw new Custom404Exception("카카오 서비스가 준비 중입니다. 잠시 후 다시 시도해 주세요. [KCR01]");
        }
    }

    //Step 2 : 토큰받기
    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<KakaoTokenDto.Response> loginCallback(String code) {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
                .redirect_uri("https://ttorang.site/oauth/kakao/callback")
                .build();

        log.info("code : {}", code);

        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        log.info("[-] kakaoToken : {}", kakaoToken);

        return ResponseEntity.ok(kakaoToken);
    }

}
