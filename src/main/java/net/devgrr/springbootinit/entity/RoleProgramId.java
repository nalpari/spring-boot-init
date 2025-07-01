package net.devgrr.springbootinit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 역할별 프로그램 복합키 클래스
 * 
 * RoleProgram 엔티티의 복합 기본키를 정의하는 클래스입니다.
 * 역할 코드와 프로그램 ID의 조합으로 유일성을 보장합니다.
 * 
 * JPA에서 복합키를 사용하기 위해서는 다음 조건을 만족해야 합니다:
 * 1. Serializable 인터페이스 구현
 * 2. 기본 생성자 존재
 * 3. equals()와 hashCode() 메소드 구현 (Lombok @Data로 자동 생성)
 * 
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @NoArgsConstructor: 기본 생성자 자동 생성
 * @AllArgsConstructor: 모든 필드를 받는 생성자 자동 생성
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleProgramId implements Serializable {
    
    /**
     * 역할 코드
     * 프로그램 권한이 부여된 역할의 고유 식별자
     */
    private String roleCode;
    
    /**
     * 프로그램 ID
     * 접근 권한이 부여된 프로그램의 고유 식별자
     */
    private String programId;
} 