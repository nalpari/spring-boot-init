package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Csr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * CSR(Customer Service Request) 엔티티에 대한 데이터 접근 인터페이스
 * Spring Data JPA의 JpaRepository를 상속받아 기본적인 CRUD 기능 제공
 * 
 * @Repository Spring Data JPA가 자동으로 구현체를 생성하는 Repository 인터페이스
 * JpaRepository<Csr, Integer> - 엔티티 타입과 기본키 타입을 제네릭으로 지정
 */
@Repository
public interface CsrRepository extends JpaRepository<Csr, Integer> {

    /**
     * 요청자 ID로 CSR 목록 조회
     * 
     * @param reqId 요청자 ID
     * @return 해당 요청자의 CSR 목록
     */
    List<Csr> findByReqId(String reqId);

    /**
     * 처리 상태로 CSR 목록 조회 (페이징)
     * 
     * @param procStatCd 처리 상태 코드
     * @param pageable 페이징 정보
     * @return 해당 처리 상태의 CSR 페이지
     */
    Page<Csr> findByProcStatCd(String procStatCd, Pageable pageable);

    /**
     * CSR 유형으로 CSR 목록 조회
     * 
     * @param csrTypeCd CSR 유형 코드
     * @return 해당 유형의 CSR 목록
     */
    List<Csr> findByCsrTypeCd(String csrTypeCd);

    /**
     * 사업부 구분으로 CSR 목록 조회
     * 
     * @param busiDivCd 사업부 구분 코드
     * @return 해당 사업부의 CSR 목록
     */
    List<Csr> findByBusiDivCd(String busiDivCd);

    /**
     * 요청 제목으로 CSR 검색 (부분 일치)
     * 
     * @param title 검색할 제목 (부분 검색어)
     * @return 제목에 검색어가 포함된 CSR 목록
     */
    List<Csr> findByReqTitleContaining(String title);

    /**
     * 요청자명으로 CSR 검색 (부분 일치)
     * 
     * @param reqNm 요청자명 (부분 검색어)
     * @return 요청자명에 검색어가 포함된 CSR 목록
     */
    List<Csr> findByReqNmContaining(String reqNm);

    /**
     * 프로그램 ID로 CSR 목록 조회
     * 
     * @param progId 프로그램 ID
     * @return 해당 프로그램과 연관된 CSR 목록
     */
    List<Csr> findByProgId(String progId);

    /**
     * 처리 상태와 CSR 유형으로 CSR 목록 조회
     * 
     * @param procStatCd 처리 상태 코드
     * @param csrTypeCd CSR 유형 코드
     * @return 조건에 맞는 CSR 목록
     */
    List<Csr> findByProcStatCdAndCsrTypeCd(String procStatCd, String csrTypeCd);

    /**
     * 요청자 ID와 처리 상태로 CSR 목록 조회
     * 
     * @param reqId 요청자 ID
     * @param procStatCd 처리 상태 코드
     * @return 조건에 맞는 CSR 목록
     */
    List<Csr> findByReqIdAndProcStatCd(String reqId, String procStatCd);

    /**
     * 특정 기간에 등록된 CSR 목록 조회
     * 
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 해당 기간에 등록된 CSR 목록
     */
    List<Csr> findByRegiDtBetween(Date startDate, Date endDate);

    /**
     * 처리 상태별 CSR 개수 조회
     * 
     * @param procStatCd 처리 상태 코드
     * @return 해당 상태의 CSR 개수
     */
    long countByProcStatCd(String procStatCd);

    /**
     * CSR 유형별 CSR 개수 조회
     * 
     * @param csrTypeCd CSR 유형 코드
     * @return 해당 유형의 CSR 개수
     */
    long countByCsrTypeCd(String csrTypeCd);

    /**
     * 사업부별 CSR 통계 조회
     * 사업부 구분 코드별로 그룹핑하여 개수 집계
     * 
     * @return 사업부별 CSR 개수 통계 (Object 배열: [사업부 코드, 개수])
     */
    @Query("SELECT c.busiDivCd, COUNT(c) FROM Csr c GROUP BY c.busiDivCd")
    List<Object[]> getCsrStatsByBusiness();

    /**
     * 월별 CSR 등록 통계 조회
     * 등록일 기준으로 월별 그룹핑하여 개수 집계
     * 
     * @return 월별 CSR 등록 통계 (Object 배열: [년월, 개수])
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', c.regiDt, '%Y-%m'), COUNT(c) FROM Csr c GROUP BY FUNCTION('DATE_FORMAT', c.regiDt, '%Y-%m') ORDER BY FUNCTION('DATE_FORMAT', c.regiDt, '%Y-%m')")
    List<Object[]> getCsrStatsMonthly();

    /**
     * 미완료 CSR 목록 조회 (완료구분이 완료가 아닌 경우)
     * 
     * @param completeCd 완료 코드 (완료를 나타내는 코드)
     * @return 미완료 CSR 목록
     */
    @Query("SELECT c FROM Csr c WHERE c.cmplDe != :completeCd OR c.cmplDe IS NULL")
    List<Csr> findIncompleteCsr(@Param("completeCd") String completeCd);

    /**
     * 요청자 ID와 CSR 유형으로 CSR 개수 조회
     * 
     * @param reqId 요청자 ID
     * @param csrTypeCd CSR 유형 코드
     * @return 조건에 맞는 CSR 개수
     */
    long countByReqIdAndCsrTypeCd(String reqId, String csrTypeCd);

    /**
     * 첨부파일이 있는 CSR 목록 조회
     * 
     * @return 첨부파일이 있는 CSR 목록
     */
    @Query("SELECT c FROM Csr c WHERE c.atchFileId IS NOT NULL AND c.atchFileId != ''")
    List<Csr> findCsrWithAttachments();

    /**
     * 긴급/중요 CSR 목록 조회 (완료희망구분이 긴급인 경우)
     * 
     * @param urgentCd 긴급 코드
     * @return 긴급 CSR 목록
     */
    List<Csr> findByCmplWishDe(String urgentCd);

    /**
     * 요청 순번으로 존재 여부 확인
     * 
     * @param reqSeq 요청 순번
     * @return 존재 여부 (true/false)
     */
    boolean existsByReqSeq(Integer reqSeq);
} 