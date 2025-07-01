package net.devgrr.springbootinit.dto;

import lombok.Data;
import net.devgrr.springbootinit.entity.Role;

/**
 * 사용자 정보 수정 요청 데이터 전송 객체 (DTO)
 * 
 * 기존 사용자의 정보를 수정할 때 사용되는 요청 데이터를 담는 클래스입니다.
 * 비밀번호는 포함하지 않으며, 별도의 비밀번호 변경 API를 통해 처리됩니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
public class UserUpdateRequest {
    
    /**
     * 수정할 사용자명
     * 새로운 사용자명으로 변경하려는 경우 사용
     * 중복 검사가 수행됩니다.
     */
    private String username;
    
    /**
     * 수정할 이메일 주소
     * 새로운 이메일 주소로 변경하려는 경우 사용
     * 이메일 형식 유효성 검사와 중복 검사가 수행됩니다.
     */
    private String email;
    
    /**
     * 수정할 사용자 역할
     * ADMIN 또는 USER 권한으로 변경하려는 경우 사용
     * 관리자 권한이 필요합니다.
     */
    private Role role;
}