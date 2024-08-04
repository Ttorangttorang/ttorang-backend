package com.ttorang.api.login.controller;

import com.ttorang.api.login.dto.OauthLoginDto;
import com.ttorang.api.login.service.OauthLoginService;
import com.ttorang.api.login.validator.OauthValidator;
import com.ttorang.domain.user.constant.UserType;
import com.ttorang.global.model.RestApiResponse;
import com.ttorang.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
public class OauthLoginController {

    private final OauthValidator oauthValidator;
    private final OauthLoginService oauthLoginService;

    @Tag(name = "authentication")
    @Operation(summary = "소셜 로그인 API", description = "소셜 로그인 API")
    @PostMapping("/login")
    public RestApiResponse<OauthLoginDto.Response> oauthLogin(
            @RequestBody OauthLoginDto.Request request,
            HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
        oauthValidator.validateUserType(request.getUserType());

        String accessToken = authorizationHeader.split(" ")[1];
        OauthLoginDto.Response response = oauthLoginService.oauthLogin(accessToken, UserType.from(request.getUserType()));

        return RestApiResponse.success(response);
    }

}












