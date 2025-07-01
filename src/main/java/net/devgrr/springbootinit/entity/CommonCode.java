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
 * 공통코드 마스터 엔티티
 * 
 * 시스템에서 사용되는 각종 코드의 분류와 기본 정보를 관리하는 테이블입니다.
 * 공통코드의 메타데이터를 저장하며, 하위 상세 코드들의 그룹 역할을 담당합니다.
 * 
 * 테이블명: CM_CMON_CD (Common Code Master)
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
@Table(name = "cm_cmon_cd")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommonCode {

    /**
     * 코드 ID (기본키)
     * 공통코드를 식별하는 고유한 키값
     */
    @Id
    @Column(name = "cd_id", length = 25)
    private String codeId;

    /**
     * 공통코드
     * 실제 사용되는 공통코드 값
     */
    @Column(name = "cmon_cd", length = 25)
    private String commonCode;

    /**
     * 공통코드명
     * 공통코드의 한글 또는 설명명
     */
    @Column(name = "cmon_cd_nm", length = 100)
    private String commonCodeName;

    /**
     * 줄임명
     * 공통코드의 간략한 표시명
     */
    @Column(name = "shrt_nm", length = 50)
    private String shortName;

    /**
     * 정렬순서
     * 공통코드의 표시 순서
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
     * 공통코드에 대한 상세 설명
     */
    @Column(name = "cmon_cd_desc", length = 1000)
    private String commonCodeDescription;

    /**
     * 코드 길이
     * 하위 상세 코드의 표준 길이
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
     * 공통코드 상세 목록
     * 이 공통코드에 속하는 상세 코드들의 목록
     * @OneToMany: 일대다 관계 매핑
     * cascade = CascadeType.ALL: 부모 엔티티 변경 시 자식도 함께 처리
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @OneToMany(mappedBy = "commonCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommonCodeDetail> commonCodeDetails;
} 