package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 공통코드 상세 엔티티
 * 
 * 공통코드에 속하는 실제 코드 값들과 상세 정보를 관리하는 테이블입니다.
 * 공통코드 마스터에 의해 정의된 분류에 속하는 구체적인 코드 데이터를 저장합니다.
 * 
 * 테이블명: CM_CMON_CD_D (Common Code Detail)
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
@Table(name = "cm_cmon_cd_d")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommonCodeDetail {

    /**
     * 코드 ID (기본키)
     * 상세 코드를 식별하는 고유한 키값
     */
    @Id
    @Column(name = "cd_id", length = 25)
    private String codeId;

    /**
     * 공통코드
     * 이 상세 코드가 속하는 공통코드 그룹
     */
    @Column(name = "cmon_cd", length = 25)
    private String commonCode;

    /**
     * 공통코드명
     * 상세 코드의 한글 또는 설명명
     */
    @Column(name = "cmon_cd_nm", length = 100)
    private String commonCodeName;

    /**
     * 줄임명
     * 상세 코드의 간략한 표시명
     */
    @Column(name = "shrt_nm", length = 50)
    private String shortName;

    /**
     * 정렬순서
     * 상세 코드의 표시 순서
     */
    @Column(name = "sort_ord")
    private Integer sortOrder;

    /**
     * 사용여부
     * 'Y': 사용, 'N': 미사용
     */
    @Column(name = "use_yn", length = 1)
    private String useYn;

    /**
     * 공통코드 설명
     * 상세 코드에 대한 상세 설명
     */
    @Column(name = "cmon_cd_desc", length = 1000)
    private String commonCodeDescription;

    /**
     * 코드 길이
     * 상세 코드의 길이
     */
    @Column(name = "cd_len")
    private Integer codeLength;

    /**
     * 비고
     * 추가적인 메모나 설명
     */
    @Column(name = "rmrk", length = 4000)
    private String remark;

    /**
     * 등록자 ID
     * 데이터를 최초 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 데이터 최초 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 데이터를 마지막으로 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 데이터 마지막 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 참조값 1
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_1", length = 250)
    private String referenceValue1;

    /**
     * 참조값 2
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_2", length = 250)
    private String referenceValue2;

    /**
     * 참조값 3
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_3", length = 250)
    private String referenceValue3;

    /**
     * 참조값 4
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_4", length = 250)
    private String referenceValue4;

    /**
     * 참조값 5
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_5", length = 250)
    private String referenceValue5;

    /**
     * 참조값 6
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_6", length = 250)
    private String referenceValue6;

    /**
     * 참조값 7
     * 추가적인 참조 데이터 저장용 필드
     */
    @Column(name = "refr_val_7", length = 250)
    private String referenceValue7;

    /**
     * 참조 숫자값 1
     * 숫자형 참조 데이터 저장용 필드 (정밀도 20, 소수점 5자리)
     */
    @Column(name = "rnum_val_1", precision = 20, scale = 5)
    private BigDecimal referenceNumericValue1;

    /**
     * 참조 숫자값 2
     * 숫자형 참조 데이터 저장용 필드 (정밀도 20, 소수점 5자리)
     */
    @Column(name = "rnum_val_2", precision = 20, scale = 5)
    private BigDecimal referenceNumericValue2;

    /**
     * 공통코드 마스터 연관관계
     * 이 상세 코드가 속하는 공통코드 마스터 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmon_cd", referencedColumnName = "cmon_cd", insertable = false, updatable = false)
    private CommonCode commonCode;
} 