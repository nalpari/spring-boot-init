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
 * 공통코드 스펙 엔티티
 * 
 * 공통코드의 상세 스펙(specification)을 정의하는 테이블입니다.
 * 공통코드의 구체적인 값들과 참조값들을 관리하며,
 * 시스템 전반에서 사용되는 코드성 데이터의 확장 정보를 저장합니다.
 * 
 * 테이블명: CM_CMON_CD_SPEC (Common Code Specification)
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
@Table(name = "cm_cmon_cd_spec")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommonCodeSpec {

    /**
     * 코드 ID (기본키)
     * 공통코드 스펙을 식별하는 고유한 키값
     */
    @Id
    @Column(name = "cd_id", length = 25)
    private String codeId;

    /**
     * 공통코드
     * 연결된 공통코드의 값
     */
    @Column(name = "cmon_cd", length = 25)
    private String commonCode;

    /**
     * 공통코드 스펙명
     * 이 스펙의 이름 또는 설명
     */
    @Column(name = "cmon_cd_spec_nm", length = 100)
    private String commonCodeSpecName;

    /**
     * 단축명
     * 화면 표시용 간략한 이름
     */
    @Column(name = "shrt_nm", length = 50)
    private String shortName;

    /**
     * 정렬순서
     * 같은 공통코드 내에서의 표시 순서
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
     * 공통코드 스펙 설명
     * 이 스펙에 대한 상세 설명
     */
    @Column(name = "cmon_cd_spec_desc", length = 1000)
    private String commonCodeSpecDescription;

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
     * 스펙에서 사용하는 첫 번째 확장 참조값
     */
    @Column(name = "refr_val_1", length = 250)
    private String referenceValue1;

    /**
     * 참조값 2
     * 스펙에서 사용하는 두 번째 확장 참조값
     */
    @Column(name = "refr_val_2", length = 250)
    private String referenceValue2;

    /**
     * 참조값 3
     * 스펙에서 사용하는 세 번째 확장 참조값
     */
    @Column(name = "refr_val_3", length = 250)
    private String referenceValue3;

    /**
     * 참조값 4
     * 스펙에서 사용하는 네 번째 확장 참조값
     */
    @Column(name = "refr_val_4", length = 250)
    private String referenceValue4;

    /**
     * 참조값 5
     * 스펙에서 사용하는 다섯 번째 확장 참조값
     */
    @Column(name = "refr_val_5", length = 250)
    private String referenceValue5;
} 