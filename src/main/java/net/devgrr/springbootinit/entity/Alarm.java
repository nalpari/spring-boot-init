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
 * 알람 관리 엔티티
 * 
 * 시스템 내에서 발생하는 각종 알람 정보를 관리하는 엔티티입니다.
 * 사용자별 알람 설정, 알람 발송 이력, 알람 타입별 관리 등을 
 * 지원하는 핵심 알람 시스템의 기본 정보를 저장합니다.
 * 
 * 테이블명: CM_ALARM
 * 
 * @Entity: JPA 엔티티임을 나타내는 어노테이션
 * @Table: 실제 데이터베이스 테이블명 매핑
 * @IdClass: 복합키 클래스 지정
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @Builder: Lombok - 빌더 패턴 적용
 * @EntityListeners: JPA Auditing을 위한 리스너 설정
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "cm_alarm")
@IdClass(AlarmId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Alarm {

    /**
     * 알람 사용자 ID (복합키)
     * 알람을 수신할 사용자의 ID
     */
    @Id
    @Column(name = "alarm_user_id", length = 20)
    private String alarmUserId;

    /**
     * 알람 순번 (복합키)
     * 동일 사용자에 대한 알람의 순번
     */
    @Id
    @Column(name = "alarm_seq")
    private Integer alarmSequence;

    /**
     * 제목
     * 알람의 제목 또는 요약 정보
     */
    @Column(name = "titl", length = 4000)
    private String title;

    /**
     * 알람 내용
     * 알람의 상세 내용 (CLOB 타입)
     */
    @Lob
    @Column(name = "alarm_cntn")
    private String alarmContent;

    /**
     * 작성일시
     * 알람이 작성된 일시
     */
    @Column(name = "writ_dt", length = 26)
    private String writeDate;

    /**
     * 알람 시작일시
     * 알람이 활성화되는 시작 일시
     */
    @Column(name = "alarm_strt_dt", length = 26)
    private String alarmStartDate;

    /**
     * 알람 종료일시
     * 알람이 비활성화되는 종료 일시
     */
    @Column(name = "alarm_end_dt", length = 26)
    private String alarmEndDate;

    /**
     * 첨부파일 ID
     * 알람에 첨부된 파일의 ID
     */
    @Column(name = "atch_file_id", length = 20)
    private String attachFileId;

    /**
     * 등록자 ID
     * 알람을 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 알람 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 알람을 최종 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 알람 최종 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 알람 사용자 연관관계
     * 알람을 수신하는 사용자와의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumn: 외래키 컬럼 지정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_user_id", insertable = false, updatable = false)
    private User alarmUser;

    /**
     * 알람 객체 이력 연관관계
     * 이 알람의 객체별 이력과의 일대다 관계
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 연관관계의 주인이 아님을 표시 (AlarmObject.alarm 필드가 주인)
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * cascade = CascadeType.ALL: 영속성 전이 설정
     */
    @OneToMany(mappedBy = "alarm", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlarmObject> alarmObjects;
} 