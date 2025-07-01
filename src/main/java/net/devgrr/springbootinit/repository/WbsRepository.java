package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Wbs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * WBS(Work Breakdown Structure) 엔티티에 대한 데이터 접근 인터페이스
 * Spring Data JPA의 JpaRepository를 상속받아 기본적인 CRUD 기능 제공
 * 
 * @Repository Spring Data JPA가 자동으로 구현체를 생성하는 Repository 인터페이스
 * JpaRepository<Wbs, Integer> - 엔티티 타입과 기본키 타입을 제네릭으로 지정
 */
@Repository
public interface WbsRepository extends JpaRepository<Wbs, Integer> {

    /**
     * 프로그램 ID로 WBS 목록 조회
     * 
     * @param progId 프로그램 ID
     * @return 해당 프로그램과 연관된 WBS 목록
     */
    List<Wbs> findByProgId(String progId);

    /**
     * 진행 상태로 WBS 목록 조회 (페이징)
     * 
     * @param prgsStat 진행 상태 코드
     * @param pageable 페이징 정보
     * @return 해당 진행 상태의 WBS 페이지
     */
    Page<Wbs> findByPrgsStat(String prgsStat, Pageable pageable);

    /**
     * 레벨 코드로 WBS 목록 조회
     * 
     * @param levelCd 레벨 코드
     * @return 해당 레벨의 WBS 목록
     */
    List<Wbs> findByLevelCd(String levelCd);

    /**
     * 프로그램명으로 WBS 검색 (부분 일치)
     * 
     * @param progNm 프로그램명 (부분 검색어)
     * @return 프로그램명에 검색어가 포함된 WBS 목록
     */
    List<Wbs> findByProgNmContaining(String progNm);

    /**
     * 구분과 진행 상태로 WBS 목록 조회
     * 
     * @param div 구분 코드
     * @param prgsStat 진행 상태 코드
     * @return 조건에 맞는 WBS 목록
     */
    List<Wbs> findByDivAndPrgsStat(String div, String prgsStat);

    /**
     * 담당자 ID로 WBS 목록 조회 (복사 설계자 또는 복사 개발자)
     * JPQL을 사용한 복합 조건 검색
     * 
     * @param userId 담당자 ID
     * @return 해당 사용자가 담당하는 WBS 목록
     */
    @Query("SELECT w FROM Wbs w WHERE w.ccpyDsgnr = :userId OR w.ccpyDevpr = :userId")
    List<Wbs> findByAssignedUser(@Param("userId") String userId);

    /**
     * 우선순위가 높은 WBS 목록 조회 (우선순위 코드 기준)
     * 
     * @param prioCode 우선순위 코드
     * @return 지정된 우선순위 이상의 WBS 목록
     */
    @Query("SELECT w FROM Wbs w WHERE w.prio <= :prioCode ORDER BY w.prio ASC")
    List<Wbs> findHighPriorityWbs(@Param("prioCode") String prioCode);

    /**
     * 메뉴별 WBS 통계 조회
     * 메뉴1 기준으로 그룹핑하여 개수 집계
     * 
     * @return 메뉴별 WBS 개수 통계 (Object 배열: [메뉴명, 개수])
     */
    @Query("SELECT w.menu1, COUNT(w) FROM Wbs w GROUP BY w.menu1")
    List<Object[]> getWbsStatsByMenu1();

    /**
     * 진행 상태별 WBS 개수 조회
     * 
     * @param prgsStat 진행 상태 코드
     * @return 해당 상태의 WBS 개수
     */
    long countByPrgsStat(String prgsStat);

    /**
     * 개발 일수 범위로 WBS 목록 조회
     * 
     * @param minDays 최소 개발 일수
     * @param maxDays 최대 개발 일수
     * @return 개발 일수가 범위 내에 있는 WBS 목록
     */
    List<Wbs> findByDevDayBetween(Double minDays, Double maxDays);

    /**
     * 특정 기간에 등록된 WBS 목록 조회
     * 
     * @param startDate 시작일 (YYYYMMDD 형식)
     * @param endDate 종료일 (YYYYMMDD 형식)
     * @return 해당 기간에 등록된 WBS 목록
     */
    @Query("SELECT w FROM Wbs w WHERE w.ccpyPlanStartDt >= :startDate AND w.ccpyPlanEndDt <= :endDate")
    List<Wbs> findByPlanDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * WBS 번호로 존재 여부 확인
     * 
     * @param wbsNo WBS 번호
     * @return 존재 여부 (true/false)
     */
    boolean existsByWbsNo(Integer wbsNo);
} 