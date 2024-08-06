package com.ttorang.jwt.service;

import com.ttorang.domain.user.constant.Role;
import com.ttorang.global.code.ErrorCode;
import com.ttorang.global.error.exception.AuthenticationException;
import com.ttorang.jwt.constant.GrantType;
import com.ttorang.jwt.constant.TokenType;
import com.ttorang.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.ttorang.global.code.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    // yml에서 설정
    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long userId, Role role) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(userId, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(userId, refreshTokenExpireTime);

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(Long userId, Role role, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    //토큰 제목
                .setIssuedAt(new Date())                //토큰 발행일자
                .setExpiration(expirationTime)          //토큰 만료일자
                .claim("userId", userId)                //회원 아이디
                .claim("role", role.name())             //회원 권한
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8)) //토큰 암호화 알고리즘, secret값
                .setHeaderParam("typ", "JWT")           //토큰 타입
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long userId, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();

        return refreshToken;
    }

    /**
     * 토큰 검증
     */
    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            log.info("token 만료", e);
            throw new AuthenticationException(TOKEN_EXPIRED);
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(NOT_VALID_TOKEN);
        }
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(NOT_VALID_TOKEN);
        }
        return claims;
    }




}
