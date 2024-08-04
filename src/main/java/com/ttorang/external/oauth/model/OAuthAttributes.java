package com.ttorang.external.oauth.model;

import com.ttorang.domain.user.constant.Role;
import com.ttorang.domain.user.constant.UserType;
import com.ttorang.domain.user.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;
    private UserType userType;
    private String nickname;

    public User toUserEntity(UserType userType, Role role) {
        return User.builder()
                .userName(name)
                .email(email)
                .userType(userType)
                .role(role)
                .build();
    }

}
