package com.ttorang.api.clova.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateClovaResponse {
    private Status status;
    private Result result;

    @Getter @Setter
    public static class Status {
        private String code;
        private String message;
    }

    @Getter @Setter
    public static class Result {
        private Message message;
        private String stopReason;
        private int inputLength;
        private int outputLength;
        private List<AiFilter> aiFilter;
        private long seed;

        @Getter @Setter
        public static class Message {
            private String role;
            private String content;

        }

        @Getter @Setter
        public static class AiFilter {
            private String groupName;
            private String name;
            private String score;

        }
    }
}