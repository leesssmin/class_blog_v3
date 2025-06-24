package com.tenco.blog.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        // JoinDTO 를 User Object 변환하는 메서드 추가
        // 계층간 데이터 변환을 위해 명확하게 분리
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .build();
        }

        // 회원가입시 유효성 검증 메서드
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자 명은 필수야");
            }

            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비번도 필수 ^^");
            }
            //간단한 이메일 형식 검증 (정규화 표현식)
            if (email.contains("@") == false) {
                throw new IllegalArgumentException("똑바로 안쓰나?!");
            }
        }
    }

    // 로그인 용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String username;
        private String password;

        // 유효성 검사
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("야 사용자명 입력해");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("야 비번 입력해");
            }

        }
    }
}
