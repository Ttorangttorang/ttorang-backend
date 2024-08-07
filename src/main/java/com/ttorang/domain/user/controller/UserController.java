package com.ttorang.domain.user.controller;

import com.ttorang.domain.user.model.dto.response.DeleteUserResponse;
import com.ttorang.domain.user.service.UserService;
import com.ttorang.global.model.RestApiResponse;
import com.ttorang.resolver.userinfo.UserInfo;
import com.ttorang.resolver.userinfo.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "user", description = "사용자 API")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원 탈퇴 API",
            description = "회원 탈퇴를 합니다. 1달간 데이터 보존 후 삭제합니다."
    )
    @PostMapping("/withdrawal")
    public RestApiResponse<DeleteUserResponse> deleteUser(
            @UserInfo UserInfoDto userInfo) {
        return RestApiResponse.success(
                userService.deleteUserUpdateYn(userInfo.getUserId()));
    }


}
