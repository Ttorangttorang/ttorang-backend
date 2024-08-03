package com.ttorang.web.kakaotoken.dto;

import lombok.*;

//인가 코드 받은 후 토큰 받기위한 DTO
public class KakaoTokenDto {

    @Builder @Getter
    public static class Request {
        private String grant_type;
        private String client_id;
        private String redirect_uri;
        private String code;
        private String client_secret;
    }

    @ToString
    @Builder @Getter
    @NoArgsConstructor @AllArgsConstructor
    public static class Response {
        private String token_type;
        private String access_token;
        private Integer expires_in;
        private String refresh_token;
        private Integer refresh_token_expires_in;
        private String scope;
    }





}
