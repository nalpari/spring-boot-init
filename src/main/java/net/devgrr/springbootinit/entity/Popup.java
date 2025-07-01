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
 * 팝업 관리 엔티티
 * 
 * 시스템 내에서 사용되는 팝업창 정보를 관리하는 엔티티입니다.
 * 공지사항, 이벤트, 시스템 알림 등 다양한 형태의 팝업을 
 * 설정하고 관리할 수 있습니다.
 * 
 * 테이블명: CM_POPUP
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
@Table(name = "cm_popup")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Popup {

    /**
     * 팝업 ID (기본키)
     * 팝업을 식별하는 고유한 ID
     */
    @Id
    @Column(name = "popup_id", length = 30)
    private String popupId;

    /**
     * 팝업명
     * 팝업창의 제목 또는 이름
     */
    @Column(name = "popup_nm", length = 200)
    private String popupName;

    /**
     * 팝업 타입
     * 팝업의 형태나 용도를 구분하는 코드
     * (예: 공지사항, 이벤트, 시스템알림 등)
     */
    @Column(name = "popup_type", length = 25)
    private String popupType;

    /**
     * 팝업 너비
     * 팝업창의 가로 크기 (픽셀 단위)
     */
    @Column(name = "popup_wdth")
    private Integer popupWidth;

    /**
     * 팝업 높이
     * 팝업창의 세로 크기 (픽셀 단위)
     */
    @Column(name = "popup_hght")
    private Integer popupHeight;

    /**
     * 팝업 컨텐츠
     * 팝업에 표시될 HTML 컨텐츠
     */
    @Lob
    @Column(name = "popup_cntnts")
    private String popupContents;

    /**
     * 팝업 URL
     * 팝업에서 호출할 URL (iframe 방식의 경우)
     */
    @Column(name = "popup_url", length = 500)
    private String popupUrl;

    /**
     * 시작일시
     * 팝업이 활성화되는 시작 일시
     */
    @Column(name = "start_dt")
    private LocalDateTime startDate;

    /**
     * 종료일시
     * 팝업이 비활성화되는 종료 일시
     */
    @Column(name = "end_dt")
    private LocalDateTime endDate;

    /**
     * 사용 여부
     * 팝업의 활성화 상태 ('Y': 사용, 'N': 미사용)
     */
    @Column(name = "use_yn", length = 1)
    private String useYn;

    /**
     * 오늘 하루 보지 않기 여부
     * 사용자가 '오늘 하루 보지 않기' 기능을 설정할 수 있는지 여부
     */
    @Column(name = "today_not_view_yn", length = 1)
    private String todayNotViewYn;

    /**
     * 모달 팝업 여부
     * 팝업이 모달 방식인지 여부 ('Y': 모달, 'N': 일반)
     */
    @Column(name = "modal_yn", length = 1)
    private String modalYn;

    /**
     * 정렬 순서
     * 여러 팝업이 있을 때 표시 순서
     */
    @Column(name = "sort_ord")
    private Integer sortOrder;

    /**
     * 등록자 ID
     * 팝업 정보를 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 팝업 정보 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 팝업 정보를 최종 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 팝업 정보 최종 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 팝업 조회 이력 연관관계
     * 이 팝업의 조회 이력과의 일대다 관계
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 연관관계의 주인이 아님을 표시 (PopupInquiry.popup 필드가 주인)
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * cascade = CascadeType.ALL: 영속성 전이 설정
     */
    @OneToMany(mappedBy = "popup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PopupInquiry> popupInquiries;

    /**
     * 팝업 검색 이력 연관관계
     * 이 팝업의 검색 이력과의 일대다 관계
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 연관관계의 주인이 아님을 표시 (PopupSearch.popup 필드가 주인)
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * cascade = CascadeType.ALL: 영속성 전이 설정
     */
    @OneToMany(mappedBy = "popup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PopupSearch> popupSearches;
} 