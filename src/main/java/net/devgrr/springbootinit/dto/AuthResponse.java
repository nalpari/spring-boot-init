package net.devgrr.springbootinit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 인증 응답 데이터 전송 객체 (DTO)
 * 
 * 로그인 또는 회원가입 성공 시 클라이언트에게 반환되는 응답 데이터를 담는 클래스입니다.
 * JWT 토큰과 함께 사용자의 기본 정보를 포함합니다.
 * 
 * @Data: Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메소드 자동 생성
 * @AllArgsConstructor: 모든 필드를 매개변수로 하는 생성자 자동 생성
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    
    /**
     * JWT (JSON Web Token) 인증 토큰
     * 클라이언트가 후속 요청에서 Authorization 헤더에 포함하여 전송해야 하는 토큰
     * Bearer 스킴을 사용합니다. (예: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
     */
    private String token;
    
    /**
     * 인증된 사용자의 사용자명
     * 로그인에 사용된 사용자명으로, JWT 토큰의 subject에도 포함됩니다.
     */
    private String username;
    
    /**
     * 인증된 사용자의 이메일 주소
     * 사용자 계정과 연결된 이메일 주소입니다.
     */
    private String email;
}