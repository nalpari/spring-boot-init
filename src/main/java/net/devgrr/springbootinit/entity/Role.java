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
import java.util.List;

/**
 * 역할 마스터 엔티티
 * 
 * 시스템의 역할 정보를 관리하는 마스터 테이블입니다.
 * RBAC(Role-Based Access Control) 시스템의 핵심 구성요소로,
 * 사용자 권한 체계의 기본이 되는 역할 정보를 저장합니다.
 * 
 * 테이블명: CM_ROLE
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
@Table(name = "cm_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Role {

    /**
     * 역할 코드 (기본키)
     * 역할을 식별하는 고유한 코드
     */
    @Id
    @Column(name = "role_cd", length = 25)
    private String roleCode;

    /**
     * 역할명
     * 역할의 표시명 (사용자에게 보여지는 이름)
     */
    @Column(name = "role_nm", length = 100)
    private String roleName;

    /**
     * 역할 설명
     * 역할의 상세 설명 및 용도
     */
    @Column(name = "role_desc", length = 4000)
    private String roleDescription;

    /**
     * 사용 여부
     * 역할의 활성화 상태 ('Y': 사용, 'N': 미사용)
     */
    @Column(name = "use_yn", length = 1)
    private String useYn;

    /**
     * 정렬 순서
     * 역할 목록 표시 시 정렬 기준
     */
    @Column(name = "sort_ord")
    private Integer sortOrder;

    /**
     * 권한 레벨
     * 역할의 권한 수준 (숫자가 높을수록 높은 권한)
     */
    @Column(name = "auth_level")
    private Integer authLevel;

    /**
     * 등록자 ID
     * 역할 정보를 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 역할 정보 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 역할 정보를 최종 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 역할 정보 최종 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 사용자 역할 연관관계
     * 이 역할에 할당된 사용자들과의 일대다 관계
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 연관관계의 주인이 아님을 표시 (UserRole.role 필드가 주인)
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * cascade = CascadeType.ALL: 영속성 전이 설정
     */
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    /**
     * 역할 프로그램 연관관계
     * 이 역할에 할당된 프로그램들과의 일대다 관계
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 연관관계의 주인이 아님을 표시 (RoleProgram.role 필드가 주인)
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * cascade = CascadeType.ALL: 영속성 전이 설정
     */
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoleProgram> rolePrograms;
}