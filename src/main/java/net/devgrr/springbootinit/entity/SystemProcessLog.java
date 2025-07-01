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
 * 시스템 처리 로그 엔티티
 * 
 * 시스템에서 수행되는 각종 프로세스의 실행 로그를 기록하는 테이블입니다.
 * 배치 작업, 데이터 처리, 시스템 작업 등의 실행 이력과 결과를 추적할 수 있습니다.
 * 시스템 모니터링과 디버깅에 중요한 정보를 제공합니다.
 * 
 * 테이블명: CM_SYS_PROC_LOG (System Process Log)
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
@Table(name = "cm_sys_proc_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SystemProcessLog {

    /**
     * 프로그램 ID (기본키)
     * 실행된 프로그램 또는 프로세스의 고유 식별자
     */
    @Id
    @Column(name = "prog_id", length = 30)
    private String programId;

    /**
     * 생성일자
     * 로그가 생성된 날짜 (YYYYMMDD 형식)
     */
    @Column(name = "creat_dt", length = 8)
    private String createDate;

    /**
     * 생성시간
     * 로그가 생성된 시간 (HHMMSS 형식)
     */
    @Column(name = "creat_hms", length = 6)
    private String createTime;

    /**
     * 로그 코드
     * 로그의 유형을 나타내는 코드 (예: INFO, WARN, ERROR 등)
     */
    @Column(name = "log_cd", length = 3)
    private String logCode;

    /**
     * 프로세스명
     * 실행된 프로세스의 이름 또는 설명
     */
    @Column(name = "proc_nm", length = 50)
    private String processName;

    /**
     * 처리 건수
     * 프로세스가 처리한 데이터의 총 건수
     */
    @Column(name = "fetch_cnt")
    private Integer fetchCount;

    /**
     * 결과 건수
     * 프로세스 실행 결과로 생성/수정된 데이터의 건수
     */
    @Column(name = "result_cnt")
    private Integer resultCount;

    /**
     * 로그 메시지
     * 프로세스 실행 결과나 오류 메시지 등의 상세 내용
     */
    @Column(name = "log_msg", length = 4000)
    private String logMessage;

    /**
     * 로그 구분
     * 로그의 세부 구분을 나타내는 숫자 코드
     */
    @Column(name = "log_div")
    private Integer logDivision;

    /**
     * 등록일시
     * 로그 데이터 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;
} 