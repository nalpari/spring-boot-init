package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CsrCharge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * CSR 담당자 엔티티에 대한 데이터 접근 인터페이스
 * Spring Data JPA의 JpaRepository를 상속받아 기본적인 CRUD 기능 제공
 * 
 * @Repository Spring Data JPA가 자동으로 구현체를 생성하는 Repository 인터페이스
 * JpaRepository<CsrCharge, String> - 엔티티 타입과 기본키 타입을 제네릭으로 지정
 */
@Repository
public interface CsrChargeRepository extends JpaRepository<CsrCharge, String> {

    /**
     * 담당자명으로 담당자 정보 조회
     * 
     * @param chrgNm 담당자명
     * @return 해당 이름의 담당자 정보
     */
    Optional<CsrCharge> findByChrgNm(String chrgNm);

    /**
     * 담당자명으로 담당자 목록 조회 (부분 일치)
     * 
     * @param chrgNm 담당자명 (부분 검색어)
     * @return 담당자명에 검색어가 포함된 담당자 목록
     */
    List<CsrCharge> findByChrgNmContaining(String chrgNm);

    /**
     * 부서명으로 담당자 목록 조회
     * 
     * @param deptNm 부서명
     * @return 해당 부서의 담당자 목록
     */
    List<CsrCharge> findByDeptNm(String deptNm);

    /**
     * 부서명으로 담당자 목록 조회 (부분 일치)
     * 
     * @param deptNm 부서명 (부분 검색어)
     * @return 부서명에 검색어가 포함된 담당자 목록
     */
    List<CsrCharge> findByDeptNmContaining(String deptNm);

    /**
     * 전화번호로 담당자 조회
     * 
     * @param chrgTelNo 담당자 전화번호
     * @return 해당 전화번호의 담당자 정보
     */
    Optional<CsrCharge> findByChrgTelNo(String chrgTelNo);

    /**
     * 전화번호로 담당자 목록 조회 (부분 일치)
     * 
     * @param chrgTelNo 전화번호 (부분 검색어)
     * @return 전화번호에 검색어가 포함된 담당자 목록
     */
    List<CsrCharge> findByChrgTelNoContaining(String chrgTelNo);

    /**
     * 부서명으로 담당자 목록 조회 (페이징)
     * 
     * @param deptNm 부서명
     * @param pageable 페이징 정보
     * @return 해당 부서의 담당자 페이지
     */
    Page<CsrCharge> findByDeptNm(String deptNm, Pageable pageable);

    /**
     * 특정 기간에 등록된 담당자 목록 조회
     * 
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 해당 기간에 등록된 담당자 목록
     */
    List<CsrCharge> findByRegiDtBetween(Date startDate, Date endDate);

    /**
     * 특정 기간에 수정된 담당자 목록 조회
     * 
     * @param startDate 시작일
     * @param endDate 종료일
     * @return 해당 기간에 수정된 담당자 목록
     */
    List<CsrCharge> findByUpdtDtBetween(Date startDate, Date endDate);

    /**
     * 부서별 담당자 개수 조회
     * 
     * @param deptNm 부서명
     * @return 해당 부서의 담당자 개수
     */
    long countByDeptNm(String deptNm);

    /**
     * 부서별 담당자 통계 조회
     * 부서명별로 그룹핑하여 담당자 개수 집계
     * 
     * @return 부서별 담당자 개수 통계 (Object 배열: [부서명, 개수])
     */
    @Query("SELECT cc.deptNm, COUNT(cc) FROM CsrCharge cc GROUP BY cc.deptNm")
    List<Object[]> getCsrChargeStatsByDept();

    /**
     * 담당자 ID 목록으로 담당자 정보 조회
     * 
     * @param chrgIds 담당자 ID 목록
     * @return 해당 ID들의 담당자 목록
     */
    List<CsrCharge> findByChrgIdIn(List<String> chrgIds);

    /**
     * 특정 수정자가 수정한 담당자 목록 조회
     * 
     * @param updtId 수정자 ID
     * @return 해당 수정자가 수정한 담당자 목록
     */
    List<CsrCharge> findByUpdtId(String updtId);

    /**
     * 담당자명과 부서명으로 담당자 조회
     * 
     * @param chrgNm 담당자명
     * @param deptNm 부서명
     * @return 조건에 맞는 담당자 정보
     */
    Optional<CsrCharge> findByChrgNmAndDeptNm(String chrgNm, String deptNm);

    /**
     * 전체 담당자 목록을 담당자명 순으로 정렬하여 조회
     * 
     * @return 담당자명 순으로 정렬된 전체 담당자 목록
     */
    List<CsrCharge> findAllByOrderByChrgNm();

    /**
     * 전체 담당자 목록을 부서명, 담당자명 순으로 정렬하여 조회
     * 
     * @return 부서명, 담당자명 순으로 정렬된 전체 담당자 목록
     */
    List<CsrCharge> findAllByOrderByDeptNmAscChrgNmAsc();

    /**
     * 활성 담당자 목록 조회 (최근에 수정된 담당자들)
     * 특정 일자 이후에 수정된 담당자들을 활성 담당자로 간주
     * 
     * @param activeDate 활성 기준일
     * @return 활성 담당자 목록
     */
    @Query("SELECT cc FROM CsrCharge cc WHERE cc.updtDt >= :activeDate")
    List<CsrCharge> findActiveCsrCharges(@Param("activeDate") Date activeDate);

    /**
     * 담당자 검색 (통합 검색)
     * 담당자명, 부서명, 전화번호에서 키워드 검색
     * 
     * @param keyword 검색 키워드
     * @return 검색 조건에 맞는 담당자 목록
     */
    @Query("SELECT cc FROM CsrCharge cc WHERE cc.chrgNm LIKE %:keyword% OR cc.deptNm LIKE %:keyword% OR cc.chrgTelNo LIKE %:keyword%")
    List<CsrCharge> searchCsrCharges(@Param("keyword") String keyword);

    /**
     * 월별 담당자 등록 통계 조회
     * 등록일 기준으로 월별 그룹핑하여 개수 집계
     * 
     * @return 월별 담당자 등록 통계 (Object 배열: [년월, 개수])
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', cc.regiDt, '%Y-%m'), COUNT(cc) FROM CsrCharge cc GROUP BY FUNCTION('DATE_FORMAT', cc.regiDt, '%Y-%m') ORDER BY FUNCTION('DATE_FORMAT', cc.regiDt, '%Y-%m')")
    List<Object[]> getCsrChargeStatsMonthly();

    /**
     * 담당자 ID로 존재 여부 확인
     * 
     * @param chrgId 담당자 ID
     * @return 존재 여부 (true/false)
     */
    boolean existsByChrgId(String chrgId);

    /**
     * 담당자명 중복 확인
     * 
     * @param chrgNm 담당자명
     * @return 중복 여부 (true/false)
     */
    boolean existsByChrgNm(String chrgNm);

    /**
     * 전화번호 중복 확인
     * 
     * @param chrgTelNo 전화번호
     * @return 중복 여부 (true/false)
     */
    boolean existsByChrgTelNo(String chrgTelNo);
} 