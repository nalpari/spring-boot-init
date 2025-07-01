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
 * 팝업 검색 이력 엔티티
 * 
 * 팝업 관련 검색 기능의 이력을 관리하는 엔티티입니다.
 * 팝업 검색 패턴 분석, 검색 성능 개선, 사용자 검색 행태 분석 등을 
 * 지원하기 위한 검색 이력 정보를 저장합니다.
 * 
 * 테이블명: CM_POPUP_SRCH
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
@Table(name = "cm_popup_srch")
@IdClass(PopupSearchId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PopupSearch {

    /**
     * 팝업 ID (복합키)
     * 검색 대상 팝업의 식별자
     */
    @Id
    @Column(name = "popup_id", length = 30)
    private String popupId;

    /**
     * 순번 (복합키)
     * 동일 팝업에 대한 검색 이력의 순번
     */
    @Id
    @Column(name = "seq_no")
    private Integer sequenceNumber;

    /**
     * 검색어
     * 사용자가 입력한 검색어
     */
    @Column(name = "srch_nm", length = 200)
    private String searchName;

    /**
     * 컬럼명
     * 검색 대상 컬럼명 (팝업명, 컨텐츠 등)
     */
    @Column(name = "column_nm", length = 200)
    private String columnName;

    /**
     * 비교 조건
     * 검색 조건 (LIKE, EQUAL, CONTAINS 등)
     */
    @Column(name = "cmpr_cond", length = 25)
    private String compareCondition;

    /**
     * 필드 타입
     * 검색 필드의 데이터 타입 (문자열, 숫자, 날짜 등)
     */
    @Column(name = "fld_type", length = 25)
    private String fieldType;

    /**
     * 컬럼 코드
     * 검색 대상 컬럼의 시스템 코드
     */
    @Column(name = "column_cd", length = 25)
    private String columnCode;

    /**
     * 시작일자
     * 날짜 범위 검색 시 시작 날짜
     */
    @Column(name = "begin_cond_yn", length = 1)
    private String beginConditionYn;

    /**
     * 종료일자
     * 날짜 범위 검색 시 종료 날짜
     */
    @Column(name = "end_cond_yn", length = 1)
    private String endConditionYn;

    /**
     * 사용자 ID
     * 검색을 수행한 사용자 ID
     */
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 검색일시
     * 검색이 수행된 일시
     */
    @Column(name = "srch_dt")
    private LocalDateTime searchDate;

    /**
     * 검색 결과 건수
     * 검색으로 조회된 결과의 개수
     */
    @Column(name = "result_cnt")
    private Integer resultCount;

    /**
     * 검색 소요시간 (밀리초)
     * 검색 처리에 소요된 시간
     */
    @Column(name = "exec_time")
    private Long executionTime;

    /**
     * 등록자 ID
     * 검색 이력을 등록한 사용자 ID (시스템 자동 등록)
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 검색 이력 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 검색 이력을 최종 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 검색 이력 최종 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 팝업 연관관계
     * 검색 대상 팝업과의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumn: 외래키 컬럼 지정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id", insertable = false, updatable = false)
    private Popup popup;

    /**
     * 사용자 연관관계
     * 검색을 수행한 사용자와의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumn: 외래키 컬럼 지정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
} 