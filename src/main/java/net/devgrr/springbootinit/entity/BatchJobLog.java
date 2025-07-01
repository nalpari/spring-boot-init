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
 * 배치 작업 로그 엔티티
 * 
 * 시스템에서 수행되는 배치 작업의 실행 이력을 기록하는 테이블입니다.
 * 배치 작업의 시작/종료 시간, 실행 결과, 오류 메시지 등을 추적하여
 * 배치 작업의 성공/실패 여부와 성능을 모니터링할 수 있습니다.
 * 
 * 테이블명: CM_BATCH_JOB_LOG (Batch Job Log)
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
@Table(name = "cm_batch_job_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BatchJobLog {

    /**
     * 작업일자 (기본키)
     * 배치 작업이 수행된 날짜 (YYYYMMDD 형식)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_dt", length = 8)
    private String workDate;

    /**
     * 작업 순번
     * 같은 날짜에 수행된 배치 작업들의 순번
     */
    @Column(name = "work_sn")
    private Integer workSequenceNumber;

    /**
     * 프로그램 ID
     * 실행된 배치 프로그램의 고유 식별자
     */
    @Column(name = "prog_id", length = 30)
    private String programId;

    /**
     * 프로그램명
     * 실행된 배치 프로그램의 이름
     */
    @Column(name = "prog_nm", length = 4000)
    private String programName;

    /**
     * 작업 시작일시
     * 배치 작업이 시작된 일시 (YYYYMMDDHHMMSS 형식)
     */
    @Column(name = "work_bgnde", length = 14)
    private String workBeginDate;

    /**
     * 작업 종료일시
     * 배치 작업이 종료된 일시 (YYYYMMDDHHMMSS 형식)
     */
    @Column(name = "work_ende", length = 14)
    private String workEndDate;

    /**
     * 작업 결과
     * 배치 작업의 실행 결과 (SUCCESS, FAILED, ERROR 등)
     */
    @Column(name = "work_ret", length = 10)
    private String workResult;

    /**
     * 작업 결과 메시지
     * 배치 작업 실행 과정에서 발생한 메시지나 오류 내용
     */
    @Column(name = "work_ret_msg", length = 4000)
    private String workResultMessage;

    /**
     * 등록자 ID
     * 배치 작업을 실행한 사용자 또는 시스템 ID
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