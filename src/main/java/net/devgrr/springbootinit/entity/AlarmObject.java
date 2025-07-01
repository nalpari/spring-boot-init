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
 * 알람 객체 이력 엔티티
 * 
 * 알람과 관련된 객체별 이력 정보를 관리하는 엔티티입니다.
 * 알람이 발생한 대상 객체, 알람 처리 상태, 발송 결과 등을 
 * 추적하고 관리하기 위한 상세 이력 정보를 저장합니다.
 * 
 * 테이블명: CM_ALARM_OBJ
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
@Table(name = "cm_alarm_obj")
@IdClass(AlarmObjectId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AlarmObject {

    /**
     * 알람 사용자 ID (복합키)
     * 알람을 수신한 사용자의 ID
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
     * 사용자 ID (복합키)
     * 실제 사용자 ID (알람 수신자와 다를 수 있음)
     */
    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 사용자명
     * 사용자의 실명 또는 표시명
     */
    @Column(name = "user_nm", length = 50)
    private String userName;

    /**
     * 사업자/조직 구분
     * 사용자가 속한 조직의 구분 코드
     */
    @Column(name = "bcnc_div", length = 5)
    private String businessDivision;

    /**
     * 부서 구분
     * 사용자가 속한 부서의 구분 코드
     */
    @Column(name = "dstr_div", length = 5)
    private String departmentDivision;

    /**
     * 확인 여부
     * 알람 확인 상태 ('Y': 확인됨, 'N': 미확인)
     */
    @Column(name = "cnfm_yn", length = 1)
    private String confirmYn;

    /**
     * 확인 일시
     * 알람이 확인된 일시
     */
    @Column(name = "cnfm_de", length = 26)
    private String confirmDate;

    /**
     * 확인자명
     * 알람을 확인한 사용자명
     */
    @Column(name = "cnfm_tm", length = 6)
    private String confirmTime;

    /**
     * 등록자 ID
     * 알람 객체 이력을 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 알람 객체 이력 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 알람 객체 이력을 최종 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 알람 객체 이력 최종 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 알람 연관관계
     * 해당 알람과의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumns: 복합키를 가진 테이블과의 조인 설정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "alarm_user_id", referencedColumnName = "alarm_user_id", insertable = false, updatable = false),
        @JoinColumn(name = "alarm_seq", referencedColumnName = "alarm_seq", insertable = false, updatable = false)
    })
    private Alarm alarm;

    /**
     * 사용자 연관관계
     * 알람 객체와 관련된 사용자와의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumn: 외래키 컬럼 지정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
} 