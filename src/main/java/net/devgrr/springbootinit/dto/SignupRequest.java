package net.devgrr.springbootinit.dto;

import lombok.Data;

/**
 * 회원가입 요청 데이터 전송 객체 (DTO)
 * 
 * 클라이언트에서 회원가입 요청 시 전송되는 데이터를 담는 클래스입니다.
 * 사용자명, 이메일, 비밀번호 정보를 포함하며, 서버에서 유효성 검사가 수행됩니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
public class SignupRequest {
    
    /**
     * 사용자명
     * 로그인 시 사용되는 고유한 사용자 식별자
     * 중복 검사가 수행됩니다.
     */
    private String username;
    
    /**
     * 이메일 주소
     * 사용자의 이메일 주소로 고유해야 합니다.
     * 이메일 형식 유효성 검사와 중복 검사가 수행됩니다.
     */
    private String email;
    
    /**
     * 사용자 비밀번호
     * 평문으로 전송되며, 서버에서 BCrypt로 암호화되어 저장됩니다.
     */
    private String password;
}