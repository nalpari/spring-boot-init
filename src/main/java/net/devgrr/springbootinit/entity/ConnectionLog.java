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
 * 연결 로그 엔티티
 * 
 * 시스템에 대한 사용자의 연결(로그인/로그아웃) 이력을 기록하는 테이블입니다.
 * 사용자 세션 관리, 보안 감사, 접속 통계 분석 등에 활용됩니다.
 * 시스템 보안과 사용자 활동 추적에 중요한 정보를 제공합니다.
 * 
 * 테이블명: CM_CONECT_LOG
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
@Table(name = "cm_conect_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ConnectionLog {

    /**
     * 로그 번호 (기본키)
     * 연결 로그를 식별하는 고유한 순번 (자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_no", length = 14)
    private Long logNumber;

    /**
     * 사용자 ID
     * 연결을 시도한 사용자의 고유 ID
     */
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 연결 타입 코드
     * 연결의 유형을 나타내는 코드 (LOGIN, LOGOUT, TIMEOUT 등)
     */
    @Column(name = "connect_type_cd", length = 25)
    private String connectTypeCode;

    /**
     * 접근 IP
     * 사용자가 연결한 클라이언트의 IP 주소
     */
    @Column(name = "access_ip", length = 50)
    private String accessIp;

    /**
     * 서버 IP
     * 연결 요청을 처리한 서버의 IP 주소
     */
    @Column(name = "server_ip", length = 50)
    private String serverIp;

    /**
     * 연결 URL
     * 연결 시 사용된 URL 정보
     */
    @Column(name = "connect_url", length = 1000)
    private String connectUrl;

    /**
     * 비고
     * 연결에 대한 추가적인 정보나 메모
     */
    @Column(name = "rmrk", length = 4000)
    private String remark;

    /**
     * 등록일
     * 연결 로그가 등록된 날짜 (YYYYMMDD 형식)
     */
    @Column(name = "regi_de", length = 8)
    private String registerDate;

    /**
     * 등록시분초
     * 연결 로그가 등록된 시간 (HHMMSS 형식)
     */
    @Column(name = "regi_hms", length = 6)
    private String registerTime;

    /**
     * 등록일시
     * 로그 데이터 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDateTime;

    /**
     * 사용자 연관관계
     * 연결한 사용자 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
} 