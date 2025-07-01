package net.devgrr.springbootinit.dto;

import lombok.Data;
import net.devgrr.springbootinit.entity.Role;

/**
 * 사용자 생성 요청 데이터 전송 객체 (DTO)
 * 
 * 관리자가 새로운 사용자를 생성할 때 사용되는 요청 데이터를 담는 클래스입니다.
 * 회원가입과 달리 관리자가 직접 사용자의 역할을 지정할 수 있습니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
public class UserCreateRequest {
    
    /**
     * 생성할 사용자의 사용자명
     * 로그인 시 사용되는 고유한 사용자 식별자
     */
    private String username;
    
    /**
     * 생성할 사용자의 이메일 주소
     * 고유해야 하며 이메일 형식이어야 합니다.
     */
    private String email;
    
    /**
     * 생성할 사용자의 비밀번호
     * 평문으로 전송되며 서버에서 BCrypt로 암호화됩니다.
     */
    private String password;
    
    /**
     * 생성할 사용자의 역할
     * ADMIN 또는 USER 권한을 관리자가 직접 지정
     */
    private Role role;
}