package com.ttorang.resolver.userinfo;

import com.ttorang.domain.user.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class UserInfoDto {

    private Long userId;
    private Role role;

}
