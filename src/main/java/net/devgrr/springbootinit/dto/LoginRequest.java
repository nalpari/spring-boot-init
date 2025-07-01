package net.devgrr.springbootinit.dto;

import lombok.Data;

/**
 * 로그인 요청 데이터 전송 객체 (DTO)
 * 
 * 클라이언트에서 로그인 요청 시 전송되는 데이터를 담는 클래스입니다.
 * 사용자명(또는 이메일)과 비밀번호 정보를 포함합니다.
 * 
 * @Data: Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메소드 자동 생성
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
public class LoginRequest {
    
    /**
     * 사용자명 또는 이메일
     * 로그인 시 사용되는 사용자 식별자
     */
    private String username;
    
    /**
     * 사용자 비밀번호
     * 평문으로 전송되며, 서버에서 BCrypt 해시와 비교됩니다.
     */
    private String password;
}