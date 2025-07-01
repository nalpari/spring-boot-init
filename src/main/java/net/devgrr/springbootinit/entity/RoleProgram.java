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
 * 역할별 프로그램 엔티티
 * 
 * 특정 역할에 할당된 프로그램 접근 권한을 관리하는 테이블입니다.
 * RBAC(Role-Based Access Control) 시스템의 핵심 구성요소로,
 * 역할과 프로그램 간의 다대다 관계를 매핑합니다.
 * 
 * 테이블명: CM_ROLE_PROG_R
 * 
 * @Entity: JPA 엔티티임을 나타내는 어노테이션
 * @Table: 실제 데이터베이스 테이블명 매핑
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @Builder: Lombok - 빌더 패턴 적용
 * @EntityListeners: JPA Auditing을 위한 리스너 설정
 * @IdClass: 복합키 클래스 지정
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "cm_role_prog_r")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@IdClass(RoleProgramId.class)
public class RoleProgram {

    /**
     * 역할 코드 (복합키 구성요소)
     * 프로그램 권한이 부여된 역할의 코드
     */
    @Id
    @Column(name = "role_cd", length = 10)
    private String roleCode;

    /**
     * 프로그램 ID (복합키 구성요소)
     * 접근 권한이 부여된 프로그램의 고유 ID
     */
    @Id
    @Column(name = "prog_id", length = 30)
    private String programId;

    /**
     * 역할 권한
     * 이 프로그램에 대한 역할의 권한 레벨
     */
    @Column(name = "role_authority", length = 10)
    private String roleAuthority;

    /**
     * 등록자 ID
     * 역할 프로그램 권한을 등록한 관리자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 역할 프로그램 권한 할당 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 역할 프로그램 권한을 마지막으로 수정한 관리자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 역할 프로그램 권한 마지막 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 역할 연관관계
     * 프로그램 권한이 부여된 역할 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_cd", referencedColumnName = "role_cd", insertable = false, updatable = false)
    private Role role;

    /**
     * 프로그램 연관관계
     * 접근 권한이 부여된 프로그램 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_id", referencedColumnName = "prog_id", insertable = false, updatable = false)
    private Program program;
} 