package com.ttorang.api.login.service;

import com.ttorang.api.login.dto.OauthLoginDto;
import com.ttorang.domain.user.constant.Role;
import com.ttorang.domain.user.constant.UserType;
import com.ttorang.domain.user.model.entity.User;
import com.ttorang.domain.user.service.UserService;
import com.ttorang.external.oauth.kakao.service.SocialLoginApiServiceFactory;
import com.ttorang.external.oauth.model.OAuthAttributes;
import com.ttorang.external.service.SocialLoginApiService;
import com.ttorang.jwt.dto.JwtTokenDto;
import com.ttorang.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OauthLoginService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public OauthLoginDto.Response oauthLogin(String accessToken, UserType userType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(userType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}", userInfo);

        JwtTokenDto jwtTokenDto;

        Optional<User> user = userService.findUserByEmail(userInfo.getEmail());

        //신규 회원가입
        if (user.isEmpty()) {
            User oauthUser = userInfo.toUserEntity(userType, Role.ROLE_USER);
            oauthUser = userService.registUser(oauthUser);

            //accessToken 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole());
            oauthUser.updateRefreshToken(jwtTokenDto);
        } else { //기존 회원이면
            User oauthUser = user.get();
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole());
            oauthUser.updateRefreshToken(jwtTokenDto);
        }
        return OauthLoginDto.Response.of(jwtTokenDto);
    }




}