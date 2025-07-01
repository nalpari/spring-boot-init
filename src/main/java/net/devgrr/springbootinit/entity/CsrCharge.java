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
 * CSR 담당자 엔티티
 * CSR(Customer Service Request) 처리를 담당하는 담당자 정보를 관리하는 테이블
 * 
 * @Entity JPA 엔티티로 선언
 * @Table 데이터베이스 테이블 이름을 CM_CSR_CHRG로 지정
 * @EntityListeners JPA Auditing 기능을 사용하여 생성/수정 시간 자동 관리
 */
@Entity
@Table(name = "CM_CSR_CHRG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CsrCharge {

    /**
     * 담당자ID (기본키)
     * CSR 처리 담당자의 고유 식별자 (20자리)
     */
    @Id
    @Column(name = "CHRG_ID", length = 20, nullable = false)
    private String chrgId;

    /**
     * 담당자명
     * CSR 처리 담당자의 이름 (50자리)
     */
    @Column(name = "CHRG_NM", length = 50)
    private String chrgNm;

    /**
     * 담당전화번호
     * 담당자의 연락 가능한 전화번호 (20자리)
     */
    @Column(name = "CHRG_TEL_NO", length = 20)
    private String chrgTelNo;

    /**
     * 부서명
     * 담당자가 소속된 부서의 이름 (100자리)
     */
    @Column(name = "DEPT_NM", length = 100)
    private String deptNm;

    /**
     * 등록일자
     * 담당자가 등록된 날짜
     */
    @Column(name = "REGI_DT")
    private Date regiDt;

    /**
     * 수정일자
     * 담당자 정보가 수정된 날짜
     */
    @Column(name = "UPDT_DT")
    private Date updtDt;

    /**
     * 수정자
     * 담당자 정보를 수정한 사용자 (20자리)
     */
    @Column(name = "UPDT_ID", length = 20)
    private String updtId;

    /**
     * 수정일시
     * 담당자 정보가 수정된 일시
     * @LastModifiedDate JPA Auditing으로 자동 설정
     */
    @LastModifiedDate
    @Column(name = "UPDT_DT_TIME")
    private Date updtDt;

    /**
     * 연관된 CSR 메인 정보
     * @ManyToOne 다대일 관계 설정
     * 한 명의 담당자가 여러 CSR을 담당할 수 있음
     * fetch 지연 로딩 설정으로 성능 최적화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQ_SEQ", referencedColumnName = "REQ_SEQ")
    private Csr csr;
} 