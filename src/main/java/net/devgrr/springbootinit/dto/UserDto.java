package net.devgrr.springbootinit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.devgrr.springbootinit.entity.Role;

/**
 * 사용자 데이터 전송 객체 (DTO)
 * 
 * 사용자 정보를 클라이언트와 서버 간에 전송할 때 사용되는 클래스입니다.
 * 비밀번호와 같은 민감한 정보는 제외하고 안전한 정보만 포함합니다.
 * Entity와 별도로 분리하여 보안성과 유연성을 확보합니다.
 * 
 * @Data: Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메소드 자동 생성
 * @Builder: 빌더 패턴을 사용한 객체 생성 지원
 * @NoArgsConstructor: 기본 생성자 자동 생성
 * @AllArgsConstructor: 모든 필드를 매개변수로 하는 생성자 자동 생성
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    /**
     * 사용자 고유 식별자
     * 데이터베이스의 기본키로 사용되는 자동 생성 ID
     */
    private Long id;
    
    /**
     * 사용자명
     * 로그인 시 사용되는 고유한 사용자 식별자
     */
    private String username;
    
    /**
     * 이메일 주소
     * 사용자의 이메일 주소로 고유해야 합니다.
     */
    private String email;
    
    /**
     * 사용자 역할
     * ADMIN 또는 USER 권한을 나타내는 열거형 값
     */
    private Role role;
}