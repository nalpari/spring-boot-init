package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 사용자 역할 엔티티
 * 
 * 사용자에게 할당된 역할을 관리하는 테이블입니다.
 * 한 사용자는 여러 역할을 가질 수 있으며, 시스템의 권한 체계를 구성하는 핵심 엔티티입니다.
 * 역할 기반 접근 제어(RBAC)를 구현하기 위한 중간 테이블 역할을 합니다.
 * 
 * 테이블명: CM_USER_ROLE
 * 
 * @Entity: JPA 엔티티임을 나타내는 어노테이션
 * @Table: 실제 데이터베이스 테이블명 매핑
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @Builder: Lombok - 빌더 패턴 적용
 * @EntityListeners: JPA Auditing을 위한 리스너 설정
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "cm_user_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@IdClass(UserRoleId.class)
public class UserRole {

    /**
     * 역할 코드 (복합키 구성요소)
     * 사용자에게 할당된 역할의 코드
     */
    @Id
    @Column(name = "role_cd", length = 10)
    private String roleCode;

    /**
     * 사용자 ID (복합키 구성요소)
     * 역할이 할당된 사용자의 고유 ID
     */
    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 등록자 ID
     * 사용자 역할을 등록한 관리자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 사용자 역할 할당 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 사용자 역할을 마지막으로 수정한 관리자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 사용자 역할 마지막 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 역할 연관관계
     * 할당된 역할 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_cd", referencedColumnName = "role_cd", insertable = false, updatable = false)
    private Role role;

    /**
     * 사용자 연관관계
     * 역할이 할당된 사용자 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
} 