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
 * 팝업 조회 이력 엔티티
 * 
 * 사용자의 팝업 조회 이력을 관리하는 엔티티입니다.
 * 팝업 노출 통계, 사용자 반응 분석, '오늘 하루 보지 않기' 기능 등을 
 * 지원하기 위한 이력 정보를 저장합니다.
 * 
 * 테이블명: CM_POPUP_INQU
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
@Table(name = "cm_popup_inqu")
@IdClass(PopupInquiryId.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PopupInquiry {

    /**
     * 팝업 ID (복합키)
     * 조회된 팝업의 식별자
     */
    @Id
    @Column(name = "popup_id", length = 30)
    private String popupId;

    /**
     * 순번 (복합키)
     * 동일 팝업에 대한 조회 이력의 순번
     */
    @Id
    @Column(name = "seq_no")
    private Integer sequenceNumber;

    /**
     * 사용자 ID
     * 팝업을 조회한 사용자 ID
     */
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 조회일시
     * 팝업이 조회된 일시
     */
    @Column(name = "inqu_dt")
    private LocalDateTime inquiryDate;

    /**
     * 조회 타입
     * 조회 방식 구분 (팝업 표시, 클릭, 닫기 등)
     */
    @Column(name = "inqu_type", length = 25)
    private String inquiryType;

    /**
     * 디바이스 타입
     * 조회한 디바이스 종류 (PC, Mobile, Tablet 등)
     */
    @Column(name = "device_type", length = 25)
    private String deviceType;

    /**
     * 브라우저 정보
     * 조회 시 사용한 브라우저 정보
     */
    @Column(name = "browser_info", length = 200)
    private String browserInfo;

    /**
     * IP 주소
     * 조회 시 클라이언트의 IP 주소
     */
    @Column(name = "ip_addr", length = 45)
    private String ipAddress;

    /**
     * 오늘 하루 보지 않기 여부
     * 사용자가 '오늘 하루 보지 않기'를 선택했는지 여부
     */
    @Column(name = "today_not_view_yn", length = 1)
    private String todayNotViewYn;

    /**
     * 등록자 ID
     * 이력을 등록한 사용자 ID (시스템 자동 등록)
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 이력 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 이력을 최종 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 이력 최종 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 팝업 연관관계
     * 조회된 팝업과의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumn: 외래키 컬럼 지정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popup_id", insertable = false, updatable = false)
    private Popup popup;

    /**
     * 사용자 연관관계
     * 팝업을 조회한 사용자와의 다대일 관계
     * @ManyToOne: 다대일 관계 매핑
     * @JoinColumn: 외래키 컬럼 지정
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
} 