package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CsrDetail;
import net.devgrr.springbootinit.entity.CsrDetailId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * CSR 상세 엔티티에 대한 데이터 접근 인터페이스
 * Spring Data JPA의 JpaRepository를 상속받아 기본적인 CRUD 기능 제공
 * 복합키(CsrDetailId)를 사용하는 엔티티에 대한 Repository
 * 
 * @Repository Spring Data JPA가 자동으로 구현체를 생성하는 Repository 인터페이스
 * JpaRepository<CsrDetail, CsrDetailId> - 엔티티 타입과 복합키 타입을 제네릭으로 지정
 */
@Repository
public interface CsrDetailRepository extends JpaRepository<CsrDetail, CsrDetailId> {

    /**
     * 요청구분과 요청순번으로 CSR 상세 목록 조회
     * 특정 CSR에 속한 모든 처리 상세 내역을 조회
     * 
     * @param reqDe 요청구분
     * @param reqSeq 요청순번
     * @return 해당 CSR의 상세 처리 목록
     */
    List<CsrDetail> findByReqDeAndReqSeq(String reqDe, Integer reqSeq);

    /**
     * 담당자 ID로 CSR 상세 목록 조회
     * 특정 담당자가 처리한 모든 CSR 상세 내역을 조회
     * 
     * @param chrgId 담당자 ID
     * @return 해당 담당자의 CSR 상세 처리 목록
     */
    List<CsrDetail> findByChrgId(String chrgId);

    /**
     * 처리 상태로 CSR 상세 목록 조회 (페이징)
     * 
     * @param procStatCd 처리 상태 코드
     * @param pageable 페이징 정보
     * @return 해당 처리 상태의 CSR 상세 페이지
     */
    Page<CsrDetail> findByProcStatCd(String procStatCd, Pageable pageable);

    /**
     * 완료구분으로 CSR 상세 목록 조회
     * 
     * @param cmplDe 완료구분 코드
     * @return 해당 완료 상태의 CSR 상세 목록
     */
    List<CsrDetail> findByCmplDe(String cmplDe);

    /**
     * 담당자와 처리 상태로 CSR 상세 목록 조회
     * 
     * @param chrgId 담당자 ID
     * @param procStatCd 처리 상태 코드
     * @return 조건에 맞는 CSR 상세 목록
     */
    List<CsrDetail> findByChrgIdAndProcStatCd(String chrgId, String procStatCd);

    /**
     * 처리순번 순으로 정렬된 CSR 상세 목록 조회
     * 특정 CSR의 처리 과정을 시간순으로 조회
     * 
     * @param reqDe 요청구분
     * @param reqSeq 요청순번
     * @return 처리순번 순으로 정렬된 CSR 상세 목록
     */
    List<CsrDetail> findByReqDeAndReqSeqOrderByProcSeq(String reqDe, Integer reqSeq);

    /**
     * 특정 기간에 등록된 CSR 상세 목록 조회
     * 
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 해당 기간에 등록된 CSR 상세 목록
     */
    List<CsrDetail> findByRegiDtBetween(Date startDate, Date endDate);

    /**
     * 처리내용에 특정 키워드가 포함된 CSR 상세 목록 조회
     * 
     * @param keyword 검색 키워드
     * @return 처리내용에 키워드가 포함된 CSR 상세 목록
     */
    List<CsrDetail> findByProcCntnContaining(String keyword);

    /**
     * 담당자별 CSR 상세 처리 개수 조회
     * 
     * @param chrgId 담당자 ID
     * @return 해당 담당자의 CSR 상세 처리 개수
     */
    long countByChrgId(String chrgId);

    /**
     * 처리 상태별 CSR 상세 개수 조회
     * 
     * @param procStatCd 처리 상태 코드
     * @return 해당 처리 상태의 CSR 상세 개수
     */
    long countByProcStatCd(String procStatCd);

    /**
     * 특정 CSR의 처리 단계 개수 조회
     * 
     * @param reqDe 요청구분
     * @param reqSeq 요청순번
     * @return 해당 CSR의 총 처리 단계 개수
     */
    long countByReqDeAndReqSeq(String reqDe, Integer reqSeq);

    /**
     * 담당자별 처리 상태 통계 조회
     * 담당자별로 처리 상태별 개수를 집계
     * 
     * @return 담당자별 처리 상태 통계 (Object 배열: [담당자ID, 처리상태, 개수])
     */
    @Query("SELECT cd.chrgId, cd.procStatCd, COUNT(cd) FROM CsrDetail cd GROUP BY cd.chrgId, cd.procStatCd")
    List<Object[]> getCsrDetailStatsByChargeAndStatus();

    /**
     * 월별 CSR 상세 처리 통계 조회
     * 등록일 기준으로 월별 그룹핑하여 개수 집계
     * 
     * @return 월별 CSR 상세 처리 통계 (Object 배열: [년월, 개수])
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', cd.regiDt, '%Y-%m'), COUNT(cd) FROM CsrDetail cd GROUP BY FUNCTION('DATE_FORMAT', cd.regiDt, '%Y-%m') ORDER BY FUNCTION('DATE_FORMAT', cd.regiDt, '%Y-%m')")
    List<Object[]> getCsrDetailStatsMonthly();

    /**
     * 미완료 CSR 상세 목록 조회
     * 완료구분이 완료가 아니거나 NULL인 경우
     * 
     * @param completeCd 완료 코드
     * @return 미완료 CSR 상세 목록
     */
    @Query("SELECT cd FROM CsrDetail cd WHERE cd.cmplDe != :completeCd OR cd.cmplDe IS NULL")
    List<CsrDetail> findIncompleteCsrDetails(@Param("completeCd") String completeCd);

    /**
     * 특정 CSR의 최신 처리 상세 조회
     * 가장 높은 처리순번을 가진 상세 정보를 조회
     * 
     * @param reqDe 요청구분
     * @param reqSeq 요청순번
     * @return 최신 처리 상세 정보
     */
    @Query("SELECT cd FROM CsrDetail cd WHERE cd.reqDe = :reqDe AND cd.reqSeq = :reqSeq AND cd.procSeq = (SELECT MAX(cd2.procSeq) FROM CsrDetail cd2 WHERE cd2.reqDe = :reqDe AND cd2.reqSeq = :reqSeq)")
    CsrDetail findLatestCsrDetail(@Param("reqDe") String reqDe, @Param("reqSeq") Integer reqSeq);

    /**
     * 담당자의 처리 중인 CSR 상세 목록 조회
     * 특정 처리 상태에 있는 담당자의 작업 목록
     * 
     * @param chrgId 담당자 ID
     * @param procStatCds 처리 상태 코드 목록 (처리 중인 상태들)
     * @return 처리 중인 CSR 상세 목록
     */
    @Query("SELECT cd FROM CsrDetail cd WHERE cd.chrgId = :chrgId AND cd.procStatCd IN :procStatCds")
    List<CsrDetail> findInProgressCsrDetailsByCharge(@Param("chrgId") String chrgId, @Param("procStatCds") List<String> procStatCds);

    /**
     * 담당배정구분별 CSR 상세 통계 조회
     * 
     * @return 담당배정구분별 통계 (Object 배열: [담당배정구분, 개수])
     */
    @Query("SELECT cd.chrgAsgnDe, COUNT(cd) FROM CsrDetail cd GROUP BY cd.chrgAsgnDe")
    List<Object[]> getCsrDetailStatsByAssignType();

    /**
     * 처리순번의 존재 여부 확인
     * 
     * @param reqDe 요청구분
     * @param reqSeq 요청순번
     * @param procSeq 처리순번
     * @return 존재 여부 (true/false)
     */
    boolean existsByReqDeAndReqSeqAndProcSeq(String reqDe, Integer reqSeq, Integer procSeq);
} 