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
 * 접근 로그 엔티티
 * 
 * 시스템에 대한 사용자의 접근 기록을 저장하는 테이블입니다.
 * 보안 감사, 시스템 사용 패턴 분석, 문제 추적 등에 활용됩니다.
 * IP 주소, 접근 시간, 프로그램 정보 등을 기록하여 시스템 보안을 강화합니다.
 * 
 * 테이블명: CM_ACCES_LOG (Access Log)
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
@Table(name = "cm_acces_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AccessLog {

    /**
     * 로그 순번 (기본키)
     * 접근 로그를 식별하는 고유한 순번 (자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_seq_no")
    private Long logSequenceNumber;

    /**
     * 프로그램 ID
     * 접근한 프로그램이나 페이지의 식별자
     */
    @Column(name = "prog_id", length = 30)
    private String programId;

    /**
     * 접근 IP
     * 사용자가 접근한 클라이언트의 IP 주소
     */
    @Column(name = "acces_ip", length = 50)
    private String accessIp;

    /**
     * 서버 IP
     * 요청을 처리한 서버의 IP 주소
     */
    @Column(name = "server_ip", length = 50)
    private String serverIp;

    /**
     * 접근 일시
     * 실제 접근이 발생한 일시 (YYYYMMDDHHMMSS 형식)
     */
    @Column(name = "acces_dt", length = 14)
    private String accessDate;

    /**
     * 등록자 ID
     * 접근한 사용자의 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 로그 데이터 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;
} 