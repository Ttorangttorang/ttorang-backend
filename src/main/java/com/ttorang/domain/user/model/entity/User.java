package com.ttorang.domain.user.model.entity;

import com.ttorang.common.entity.BaseTimeEntity;
import com.ttorang.domain.user.constant.Role;
import com.ttorang.domain.user.constant.UserType;
import com.ttorang.global.util.DateTimeUtils;
import com.ttorang.jwt.dto.JwtTokenDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String userName;

    @Column(length = 200)
    private String profile;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Column(nullable = false, length = 1)
    private String deleteYn;

    private LocalDateTime deleteTime;

    @Builder
    public User(Long id, UserType userType, String email, String password,
                String userName, Role role, String deleteYn) {
        this.id = id;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.deleteYn = deleteYn;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        refreshToken = jwtTokenDto.getRefreshToken();
        tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void updateDeleteYnAndDelTimeNull(String deleteYn) {
        this.deleteYn = deleteYn;
        if (deleteYn.equals("N")) {
            this.deleteTime = null;
        }
    }

    public void withDraw(String deleteYn) {
        this.deleteYn = deleteYn;
        this.deleteTime = LocalDateTime.now();
    }
}
