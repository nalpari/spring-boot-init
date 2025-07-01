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
 * 프로그램 엔티티
 * 
 * 시스템의 프로그램 및 기능 모듈을 관리하는 테이블입니다.
 * 각 프로그램의 메타데이터와 권한 설정을 저장하며,
 * 사용자별, 역할별 프로그램 접근 권한 관리의 기초가 됩니다.
 * 
 * 테이블명: CM_PROG
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
@Table(name = "cm_prog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Program {

    /**
     * 프로그램 ID (기본키)
     * 프로그램을 식별하는 고유한 키값
     */
    @Id
    @Column(name = "prog_id", length = 30)
    private String programId;

    /**
     * 프로그램 파일명
     * 실제 프로그램 파일의 이름
     */
    @Column(name = "prog_file", length = 30)
    private String programFile;

    /**
     * 앱 그룹
     * 프로그램이 속한 애플리케이션 그룹
     */
    @Column(name = "app_group", length = 10)
    private String appGroup;

    /**
     * 권한
     * 프로그램에 필요한 권한 레벨
     */
    @Column(name = "authority", length = 10)
    private String authority;

    /**
     * 프로그램 타입
     * 프로그램의 유형 (WEB, API, BATCH 등)
     */
    @Column(name = "prog_type", length = 5)
    private String programType;

    /**
     * 내부 URL
     * 프로그램의 내부 접근 URL
     */
    @Column(name = "inner_url", length = 4000)
    private String innerUrl;

    /**
     * 비고
     * 프로그램에 대한 추가적인 설명이나 메모
     */
    @Column(name = "rmrk", length = 4000)
    private String remark;

    /**
     * URL
     * 프로그램의 외부 접근 URL
     */
    @Column(name = "url", length = 100)
    private String url;

    /**
     * 등록자 ID
     * 프로그램을 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 프로그램 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 프로그램을 마지막으로 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 프로그램 마지막 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 메뉴 패스워드 사용여부
     * 메뉴 접근 시 패스워드 인증 필요 여부 ('Y': 필요, 'N': 불필요)
     */
    @Column(name = "menu_pwd_use_yn", length = 1)
    private String menuPasswordUseYn;

    /**
     * 종료여부
     * 프로그램의 종료/중단 여부 ('Y': 종료, 'N': 운영중)
     */
    @Column(name = "close_yn", length = 1)
    private String closeYn;

    /**
     * 사용자별 프로그램 목록
     * 이 프로그램에 접근 권한이 있는 사용자들
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 양방향 관계에서 주인이 아닌 쪽 명시
     * cascade = CascadeType.ALL: 부모 변경 시 자식도 함께 처리
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserProgram> userPrograms;

    /**
     * 역할별 프로그램 목록
     * 이 프로그램에 접근 권한이 있는 역할들
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 양방향 관계에서 주인이 아닌 쪽 명시
     * cascade = CascadeType.ALL: 부모 변경 시 자식도 함께 처리
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoleProgram> rolePrograms;
} 