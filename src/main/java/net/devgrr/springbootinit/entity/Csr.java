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
import java.util.List;

/**
 * CSR(Customer Service Request) 엔티티
 * 고객 서비스 요청 정보를 관리하는 메인 테이블
 * 
 * @Entity JPA 엔티티로 선언
 * @Table 데이터베이스 테이블 이름을 CM_CSR로 지정
 * @EntityListeners JPA Auditing 기능을 사용하여 생성/수정 시간 자동 관리
 */
@Entity
@Table(name = "CM_CSR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Csr {

    /**
     * 요청구분
     * CSR 요청의 구분 코드 (8자리)
     */
    @Column(name = "REQ_DE", length = 8)
    private String reqDe;

    /**
     * 요청순번 (기본키)
     * CSR 요청의 순차적 번호 (5자리)
     */
    @Id
    @Column(name = "REQ_SEQ", precision = 5, nullable = false)
    private Integer reqSeq;

    /**
     * 사업부구분코드
     * 해당 CSR을 담당하는 사업부의 구분 코드 (5자리)
     */
    @Column(name = "BUSI_DIV_CD", length = 5)
    private String busiDivCd;

    /**
     * 세부사업부ID
     * 더 세분화된 사업부 식별자 (30자리)
     */
    @Column(name = "SUB_DIV_ID", length = 30)
    private String subDivId;

    /**
     * 프로그램ID
     * CSR과 연관된 프로그램 식별자 (30자리)
     */
    @Column(name = "PROG_ID", length = 30)
    private String progId;

    /**
     * 처리상태코드
     * CSR의 현재 처리 상태를 나타내는 코드 (5자리)
     */
    @Column(name = "PROC_STAT_CD", length = 5)
    private String procStatCd;

    /**
     * CSR유형코드
     * CSR의 유형을 분류하는 코드 (5자리)
     */
    @Column(name = "CSR_TYPE_CD", length = 5)
    private String csrTypeCd;

    /**
     * 완료희망구분
     * 완료를 희망하는 시점에 대한 구분 (8자리)
     */
    @Column(name = "CMPL_WISH_DE", length = 8)
    private String cmplWishDe;

    /**
     * 요청ID
     * CSR을 요청한 사용자의 ID (20자리)
     */
    @Column(name = "REQ_ID", length = 20)
    private String reqId;

    /**
     * 요청명
     * CSR을 요청한 사용자의 이름 (50자리)
     */
    @Column(name = "REQ_NM", length = 50)
    private String reqNm;

    /**
     * 요청전화번호
     * CSR을 요청한 사용자의 전화번호 (20자리)
     */
    @Column(name = "REQ_TEL_NO", length = 20)
    private String reqTelNo;

    /**
     * 요청제목
     * CSR 요청의 제목 (4000자리)
     */
    @Column(name = "REQ_TITLE", length = 4000)
    private String reqTitle;

    /**
     * 요청의견내용
     * CSR 요청의 상세 내용 (4000자리)
     */
    @Column(name = "REQ_OPNN_CNTN", length = 4000)
    private String reqOpnnCntn;

    /**
     * 완료구분
     * CSR의 완료 상태 구분 (8자리)
     */
    @Column(name = "CMPL_DE", length = 8)
    private String cmplDe;

    /**
     * 첨부파일ID
     * CSR과 연관된 첨부파일의 ID (20자리)
     */
    @Column(name = "ATCH_FILE_ID", length = 20)
    private String atchFileId;

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
     * CSR 상세 목록
     * 이 CSR과 연관된 상세 정보들
     * @OneToMany 일대다 관계 설정
     * mappedBy CSR 상세 엔티티의 csr 필드와 매핑
     * cascade 영속성 전이 설정 (ALL: 모든 영속성 상태 변화를 자식에게 전파)
     * fetch 지연 로딩 설정
     */
    @OneToMany(mappedBy = "csr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CsrDetail> csrDetails;

    /**
     * CSR 담당자 목록
     * 이 CSR과 연관된 담당자들
     * @OneToMany 일대다 관계 설정
     * mappedBy CSR 담당자 엔티티의 csr 필드와 매핑
     * cascade 영속성 전이 설정
     * fetch 지연 로딩 설정
     */
    @OneToMany(mappedBy = "csr", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CsrCharge> csrCharges;
} 