package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * CSR 상세 엔티티
 * CSR(Customer Service Request)의 처리 상세 정보를 관리하는 테이블
 * 
 * @Entity JPA 엔티티로 선언
 * @Table 데이터베이스 테이블 이름을 CM_CSR_D로 지정
 * @IdClass 복합키 클래스 지정
 * @EntityListeners JPA Auditing 기능을 사용하여 생성/수정 시간 자동 관리
 */
@Entity
@Table(name = "CM_CSR_D")
@IdClass(CsrDetailId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CsrDetail {

    /**
     * 요청구분 (복합키 1)
     * CSR 요청의 구분 코드 (8자리)
     */
    @Id
    @Column(name = "REQ_DE", length = 8)
    private String reqDe;

    /**
     * 요청순번 (복합키 2)
     * CSR 요청의 순차적 번호 (5자리)
     */
    @Id
    @Column(name = "REQ_SEQ", precision = 5)
    private Integer reqSeq;

    /**
     * 처리순번 (복합키 3)
     * CSR 처리의 순차적 번호 (3자리)
     */
    @Id
    @Column(name = "PROC_SEQ", precision = 3)
    private Integer procSeq;

    /**
     * 처리상태코드
     * 현재 처리 단계의 상태 코드 (5자리)
     */
    @Column(name = "PROC_STAT_CD", length = 5)
    private String procStatCd;

    /**
     * 담당자ID
     * 이 처리 단계를 담당하는 사용자의 ID (20자리)
     */
    @Column(name = "CHRG_ID", length = 20)
    private String chrgId;

    /**
     * 담당배정구분
     * 담당자 배정 방식에 대한 구분 (8자리)
     */
    @Column(name = "CHRG_ASGN_DE", length = 8)
    private String chrgAsgnDe;

    /**
     * 담당전화번호
     * 담당자의 연락 가능한 전화번호 (20자리)
     */
    @Column(name = "CHRG_TEL_NO", length = 20)
    private String chrgTelNo;

    /**
     * 완료예상구분
     * 작업 완료 예상 시점에 대한 구분 (8자리)
     */
    @Column(name = "CMPL_ESTIM_DE", length = 8)
    private String cmplEstimDe;

    /**
     * 완료구분
     * 실제 작업 완료 상태 구분 (8자리)
     */
    @Column(name = "CMPL_DE", length = 8)
    private String cmplDe;

    /**
     * 처리내용
     * 이 처리 단계에서 수행된 작업의 상세 내용 (4000자리)
     */
    @Column(name = "PROC_CNTN", length = 4000)
    private String procCntn;

    /**
     * 등록자ID
     * 데이터를 등록한 사용자의 ID (20자리)
     */
    @Column(name = "REGI_ID", length = 20)
    private String regiId;

    /**
     * 등록일시
     * 데이터가 등록된 일시
     * @CreatedDate JPA Auditing으로 자동 설정
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private Date regiDt;

    /**
     * 수정자ID
     * 데이터를 수정한 사용자의 ID (20자리)
     */
    @Column(name = "UPDT_ID", length = 20)
    private String updtId;

    /**
     * 수정일시
     * 데이터가 수정된 일시
     * @LastModifiedDate JPA Auditing으로 자동 설정
     */
    @LastModifiedDate
    @Column(name = "UPDT_DT")
    private Date updtDt;

    /**
     * 연관된 CSR 메인 정보
     * @ManyToOne 다대일 관계 설정
     * @JoinColumns 복합키를 사용한 조인 설정
     * fetch 지연 로딩 설정으로 성능 최적화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "REQ_DE", referencedColumnName = "REQ_DE", insertable = false, updatable = false),
        @JoinColumn(name = "REQ_SEQ", referencedColumnName = "REQ_SEQ", insertable = false, updatable = false)
    })
    private Csr csr;
} 