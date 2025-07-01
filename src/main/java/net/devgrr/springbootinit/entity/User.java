package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 사용자 엔티티 클래스
 * 
 * 시스템 사용자 정보를 저장하는 JPA 엔티티입니다.
 * Spring Security의 UserDetails 인터페이스를 구현하여 인증과 권한 부여 기능을 제공합니다.
 * 
 * @Entity: JPA 엔티티임을 나타내는 어노테이션
 * @Table: 데이터베이스 테이블명 지정 ("users")
 * @Data: Lombok 어노테이션으로 getter, setter, toString, equals, hashCode 메소드 자동 생성
 * @Builder: 빌더 패턴을 사용한 객체 생성 지원
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * 사용자 고유 식별자 (Primary Key)
     * 데이터베이스에서 자동으로 생성되는 IDENTITY 전략 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자명
     * 로그인 시 사용되는 고유한 식별자
     * 중복 불가 (unique = true), 필수 값 (nullable = false)
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * 이메일 주소
     * 사용자의 이메일 주소로 고유해야 함
     * 중복 불가 (unique = true), 필수 값 (nullable = false)
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * 비밀번호
     * BCrypt로 암호화되어 저장되는 사용자 비밀번호
     * 필수 값 (nullable = false)
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 역할
     * ADMIN 또는 USER 권한을 나타내는 열거형
     * STRING 타입으로 데이터베이스에 저장
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Spring Security UserDetails 인터페이스 구현 메소드
     * 사용자의 권한 목록을 반환합니다.
     * 
     * @return Collection<GrantedAuthority> 사용자의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 역할에 "ROLE_" 접두사를 추가하여 Spring Security 표준 형식으로 변환
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * 계정 만료 여부를 반환합니다.
     * 현재 구현에서는 항상 만료되지 않은 것으로 처리
     * 
     * @return boolean 계정이 만료되지 않았으면 true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠금 여부를 반환합니다.
     * 현재 구현에서는 항상 잠금되지 않은 것으로 처리
     * 
     * @return boolean 계정이 잠금되지 않았으면 true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명(비밀번호) 만료 여부를 반환합니다.
     * 현재 구현에서는 항상 만료되지 않은 것으로 처리
     * 
     * @return boolean 자격 증명이 만료되지 않았으면 true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정 활성화 여부를 반환합니다.
     * 현재 구현에서는 항상 활성화된 것으로 처리
     * 
     * @return boolean 계정이 활성화되어 있으면 true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}