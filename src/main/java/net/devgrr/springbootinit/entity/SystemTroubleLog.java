package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 시스템 장애 로그 엔티티
 * 
 * 시스템에서 발생하는 각종 오류, 예외, 장애 상황을 기록하는 테이블입니다.
 * 시스템 안정성 모니터링, 장애 분석, 성능 개선 등에 활용되며,
 * 서비스 운영과 유지보수에 중요한 정보를 제공합니다.
 * 
 * 테이블명: CM_SYS_TRBL_LOG
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
@Table(name = "cm_sys_trbl_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SystemTroubleLog {

    /**
     * 로그 번호 (기본키)
     * 시스템 장애 로그를 식별하는 고유한 순번 (자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_no", length = 14)
    private Long logNumber;

    /**
     * 오류 순번
     * 같은 시점에 발생한 오류들의 순번
     */
    @Column(name = "err_sj", length = 2000)
    private String errorSubject;

    /**
     * 사용자 ID
     * 장애 발생 시점의 사용자 ID (해당되는 경우)
     */
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 접근 IP
     * 장애 발생 시 클라이언트의 IP 주소
     */
    @Column(name = "access_ip", length = 50)
    private String accessIp;

    /**
     * 서버 IP
     * 장애가 발생한 서버의 IP 주소
     */
    @Column(name = "server_ip", length = 50)
    private String serverIp;

    /**
     * 발생 일시
     * 장애가 발생한 정확한 일시 (YYYYMMDDHHMMSS 형식)
     */
    @Column(name = "occu_dt", length = 14)
    private String occurDate;

    /**
     * 프로그램 ID
     * 장애가 발생한 프로그램의 식별자
     */
    @Column(name = "prog_id", length = 30)
    private String programId;

    /**
     * 접근 URL
     * 장애 발생 시 접근하고 있던 URL
     */
    @Column(name = "acces_url", length = 500)
    private String accessUrl;

    /**
     * 접근 파라미터
     * 장애 발생 시 전달된 파라미터 정보
     */
    @Column(name = "acces_param", length = 4000)
    private String accessParameter;

    /**
     * 오류 콜 넘버
     * 오류 발생 위치나 콜스택 정보
     */
    @Column(name = "err_cn", length = 4000)
    private String errorCallNumber;

    /**
     * 등록일시
     * 장애 로그 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 사용자 연관관계
     * 장애 발생 시점의 사용자 정보 참조 (해당되는 경우)
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    /**
     * 프로그램 연관관계
     * 장애가 발생한 프로그램 정보 참조 (해당되는 경우)
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_id", referencedColumnName = "prog_id", insertable = false, updatable = false)
    private Program program;
} 