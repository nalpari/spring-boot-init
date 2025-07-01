package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.PopupSearch;
import net.devgrr.springbootinit.entity.PopupSearchId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 팝업 검색 이력 리포지토리 인터페이스
 * 
 * PopupSearch 엔티티에 대한 데이터베이스 접근 기능을 제공하는 Repository 인터페이스입니다.
 * Spring Data JPA를 활용하여 팝업 검색 이력 관리, 검색 패턴 분석, 성능 모니터링 등의 
 * 기능을 지원하며, 검색 최적화를 위한 다양한 분석 쿼리 메서드를 제공합니다.
 * 
 * 주요 기능:
 * - 팝업 검색 이력 기본 CRUD 연산
 * - 사용자별 검색 패턴 분석
 * - 검색어별 통계 분석
 * - 검색 성능 모니터링
 * - 인기 검색어 추출
 * 
 * @Repository: Spring의 Repository 컴포넌트임을 나타내는 어노테이션
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface PopupSearchRepository extends JpaRepository<PopupSearch, PopupSearchId> {

    /**
     * 특정 팝업의 검색 이력 목록 조회
     * 
     * @param popupId 팝업 ID
     * @return 해당 팝업의 검색 이력 목록
     */
    List<PopupSearch> findByPopupId(String popupId);

    /**
     * 특정 사용자의 검색 이력 목록 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 검색 이력 목록
     */
    List<PopupSearch> findByUserId(String userId);

    /**
     * 특정 검색어의 검색 이력 목록 조회
     * 
     * @param searchName 검색어
     * @return 해당 검색어의 검색 이력 목록
     */
    List<PopupSearch> findBySearchName(String searchName);

    /**
     * 검색어 포함 검색 (부분 일치)
     * 
     * @param searchName 검색어 (부분 일치)
     * @return 검색어에 해당 문자열이 포함된 검색 이력 목록
     */
    List<PopupSearch> findBySearchNameContainingIgnoreCase(String searchName);

    /**
     * 특정 컬럼에 대한 검색 이력 목록 조회
     * 
     * @param columnName 컬럼명
     * @return 해당 컬럼에 대한 검색 이력 목록
     */
    List<PopupSearch> findByColumnName(String columnName);

    /**
     * 비교 조건별 검색 이력 목록 조회
     * 
     * @param compareCondition 비교 조건
     * @return 해당 비교 조건의 검색 이력 목록
     */
    List<PopupSearch> findByCompareCondition(String compareCondition);

    /**
     * 필드 타입별 검색 이력 목록 조회
     * 
     * @param fieldType 필드 타입
     * @return 해당 필드 타입의 검색 이력 목록
     */
    List<PopupSearch> findByFieldType(String fieldType);

    /**
     * 특정 기간의 검색 이력 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 검색 이력 목록
     */
    List<PopupSearch> findBySearchDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 기간의 검색 이력 목록 조회 (페이징)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @param pageable 페이징 정보
     * @return 해당 기간의 검색 이력 목록 (페이징)
     */
    Page<PopupSearch> findBySearchDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * 결과가 있는 검색 이력 목록 조회
     * 
     * @return 결과가 있는 검색 이력 목록
     */
    @Query("SELECT ps FROM PopupSearch ps WHERE ps.resultCount > 0")
    List<PopupSearch> findSearchesWithResults();

    /**
     * 결과가 없는 검색 이력 목록 조회
     * 
     * @return 결과가 없는 검색 이력 목록
     */
    @Query("SELECT ps FROM PopupSearch ps WHERE ps.resultCount = 0")
    List<PopupSearch> findSearchesWithoutResults();

    /**
     * 특정 검색어의 검색 횟수 계산
     * 
     * @param searchName 검색어
     * @return 해당 검색어의 총 검색 횟수
     */
    long countBySearchName(String searchName);

    /**
     * 특정 사용자의 검색 횟수 계산
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 총 검색 횟수
     */
    long countByUserId(String userId);

    /**
     * 특정 기간의 검색 횟수 계산
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 총 검색 횟수
     */
    long countBySearchDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 인기 검색어 목록 조회 (검색 횟수 기준)
     * 
     * @param limit 조회할 개수
     * @return 인기 검색어 목록
     */
    @Query("SELECT ps.searchName, COUNT(*) as searchCount " +
           "FROM PopupSearch ps " +
           "WHERE ps.searchName IS NOT NULL " +
           "AND ps.searchName != '' " +
           "GROUP BY ps.searchName " +
           "ORDER BY searchCount DESC " +
           "LIMIT :limit")
    List<Object[]> findTopSearchTerms(@Param("limit") int limit);

    /**
     * 검색 성능 통계 조회 (평균 실행 시간, 결과 수 등)
     * 
     * @return 검색 성능 통계 정보
     */
    @Query("SELECT AVG(ps.executionTime) as avgExecutionTime, " +
           "MIN(ps.executionTime) as minExecutionTime, " +
           "MAX(ps.executionTime) as maxExecutionTime, " +
           "AVG(ps.resultCount) as avgResultCount, " +
           "COUNT(*) as totalSearches " +
           "FROM PopupSearch ps " +
           "WHERE ps.executionTime IS NOT NULL")
    Object[] findSearchPerformanceStatistics();

    /**
     * 컬럼별 검색 통계 조회
     * 
     * @return 컬럼별 검색 통계 정보
     */
    @Query("SELECT ps.columnName, " +
           "COUNT(*) as searchCount, " +
           "AVG(ps.resultCount) as avgResultCount, " +
           "AVG(ps.executionTime) as avgExecutionTime " +
           "FROM PopupSearch ps " +
           "WHERE ps.columnName IS NOT NULL " +
           "GROUP BY ps.columnName " +
           "ORDER BY searchCount DESC")
    List<Object[]> findColumnSearchStatistics();

    /**
     * 비교 조건별 검색 통계 조회
     * 
     * @return 비교 조건별 검색 통계 정보
     */
    @Query("SELECT ps.compareCondition, " +
           "COUNT(*) as searchCount, " +
           "AVG(ps.resultCount) as avgResultCount, " +
           "AVG(ps.executionTime) as avgExecutionTime " +
           "FROM PopupSearch ps " +
           "WHERE ps.compareCondition IS NOT NULL " +
           "GROUP BY ps.compareCondition " +
           "ORDER BY searchCount DESC")
    List<Object[]> findCompareConditionStatistics();

    /**
     * 일별 검색 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 일별 검색 통계 정보
     */
    @Query("SELECT DATE(ps.searchDate) as searchDate, " +
           "COUNT(*) as totalSearches, " +
           "COUNT(DISTINCT ps.userId) as uniqueUsers, " +
           "COUNT(DISTINCT ps.searchName) as uniqueSearchTerms, " +
           "AVG(ps.executionTime) as avgExecutionTime " +
           "FROM PopupSearch ps " +
           "WHERE ps.searchDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(ps.searchDate) " +
           "ORDER BY searchDate DESC")
    List<Object[]> findDailySearchStatistics(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * 시간대별 검색 통계 조회
     * 
     * @return 시간대별 검색 통계 정보
     */
    @Query("SELECT HOUR(ps.searchDate) as hour, " +
           "COUNT(*) as totalSearches, " +
           "COUNT(DISTINCT ps.userId) as uniqueUsers, " +
           "AVG(ps.executionTime) as avgExecutionTime " +
           "FROM PopupSearch ps " +
           "GROUP BY HOUR(ps.searchDate) " +
           "ORDER BY hour")
    List<Object[]> findHourlySearchStatistics();

    /**
     * 사용자별 검색 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 사용자별 검색 통계 정보
     */
    @Query("SELECT ps.userId, " +
           "COUNT(*) as totalSearches, " +
           "COUNT(DISTINCT ps.searchName) as uniqueSearchTerms, " +
           "AVG(ps.resultCount) as avgResultCount, " +
           "MAX(ps.searchDate) as lastSearchDate " +
           "FROM PopupSearch ps " +
           "WHERE ps.searchDate BETWEEN :startDate AND :endDate " +
           "AND ps.userId IS NOT NULL " +
           "GROUP BY ps.userId " +
           "ORDER BY totalSearches DESC")
    List<Object[]> findUserSearchStatistics(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    /**
     * 느린 검색 쿼리 조회 (성능 모니터링용)
     * 
     * @param thresholdMs 임계값 (밀리초)
     * @return 임계값을 초과한 검색 이력 목록
     */
    @Query("SELECT ps FROM PopupSearch ps " +
           "WHERE ps.executionTime > :thresholdMs " +
           "ORDER BY ps.executionTime DESC")
    List<PopupSearch> findSlowSearches(@Param("thresholdMs") Long thresholdMs);

    /**
     * 검색 실패율이 높은 검색어 조회
     * 
     * @param minSearchCount 최소 검색 횟수
     * @param maxFailureRate 최대 실패율 (0.0 ~ 1.0)
     * @return 실패율이 높은 검색어 목록
     */
    @Query("SELECT ps.searchName, " +
           "COUNT(*) as totalSearches, " +
           "SUM(CASE WHEN ps.resultCount = 0 THEN 1 ELSE 0 END) as failedSearches, " +
           "(SUM(CASE WHEN ps.resultCount = 0 THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) as failureRate " +
           "FROM PopupSearch ps " +
           "WHERE ps.searchName IS NOT NULL " +
           "GROUP BY ps.searchName " +
           "HAVING COUNT(*) >= :minSearchCount " +
           "AND (SUM(CASE WHEN ps.resultCount = 0 THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) <= :maxFailureRate " +
           "ORDER BY failureRate DESC")
    List<Object[]> findHighFailureRateSearchTerms(@Param("minSearchCount") long minSearchCount,
                                                 @Param("maxFailureRate") double maxFailureRate);

    /**
     * 특정 팝업의 최근 검색 이력 조회
     * 
     * @param popupId 팝업 ID
     * @param limit 조회할 개수
     * @return 해당 팝업의 최근 검색 이력
     */
    @Query("SELECT ps FROM PopupSearch ps " +
           "WHERE ps.popupId = :popupId " +
           "ORDER BY ps.searchDate DESC " +
           "LIMIT :limit")
    List<PopupSearch> findRecentSearchesByPopup(@Param("popupId") String popupId,
                                              @Param("limit") int limit);

    /**
     * 검색 결과가 많은 검색 이력 조회 (인기 컨텐츠 분석용)
     * 
     * @param minResultCount 최소 결과 수
     * @return 결과가 많은 검색 이력 목록
     */
    @Query("SELECT ps FROM PopupSearch ps " +
           "WHERE ps.resultCount >= :minResultCount " +
           "ORDER BY ps.resultCount DESC")
    List<PopupSearch> findSearchesWithManyResults(@Param("minResultCount") int minResultCount);
} 