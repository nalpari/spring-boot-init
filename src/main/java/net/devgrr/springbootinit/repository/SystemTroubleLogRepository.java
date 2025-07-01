package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.SystemTroubleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 시스템 장애 로그 Repository 인터페이스
 * 
 * 시스템 장애 및 오류 이력에 대한 데이터 접근을 담당하는 Repository입니다.
 * 시스템 안정성 모니터링, 장애 분석, 성능 개선을 위한 데이터를 제공합니다.
 * 
 * 주요 기능:
 * - 장애 유형별 로그 조회
 * - 프로그램별 장애 이력 추적
 * - 시간대별 장애 통계
 * - 장애 패턴 분석 지원
 * 
 * @Repository: Spring의 Repository 컴포넌트 등록
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface SystemTroubleLogRepository extends JpaRepository<SystemTroubleLog, Long> {

    /**
     * 특정 사용자와 관련된 시스템 장애 로그 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 관련 시스템 장애 로그 목록
     */
    List<SystemTroubleLog> findByUserId(String userId);

    /**
     * 특정 프로그램의 시스템 장애 로그 조회
     * 
     * @param programId 프로그램 ID
     * @return 프로그램 관련 시스템 장애 로그 목록
     */
    List<SystemTroubleLog> findByProgramId(String programId);

    /**
     * 특정 IP 주소에서 발생한 시스템 장애 로그 조회
     * 
     * @param accessIp 접근 IP 주소
     * @return 해당 IP의 시스템 장애 로그 목록
     */
    List<SystemTroubleLog> findByAccessIp(String accessIp);

    /**
     * 특정 서버 IP에서 발생한 시스템 장애 로그 조회
     * 
     * @param serverIp 서버 IP 주소
     * @return 해당 서버의 시스템 장애 로그 목록
     */
    List<SystemTroubleLog> findByServerIp(String serverIp);

    /**
     * 특정 기간의 시스템 장애 로그 조회
     * 
     * @param startDate 시작 일시 (YYYYMMDDHHMMSS 형식)
     * @param endDate 종료 일시 (YYYYMMDDHHMMSS 형식)
     * @return 해당 기간의 시스템 장애 로그 목록
     */
    @Query("SELECT stl FROM SystemTroubleLog stl WHERE stl.occurDate BETWEEN :startDate AND :endDate ORDER BY stl.occurDate DESC")
    List<SystemTroubleLog> findByOccurDateBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 특정 오류 주제의 시스템 장애 로그 조회
     * 
     * @param errorSubject 오류 주제
     * @return 해당 오류 주제의 시스템 장애 로그 목록
     */
    List<SystemTroubleLog> findByErrorSubjectContaining(String errorSubject);

    /**
     * 특정 URL에서 발생한 시스템 장애 로그 조회
     * 
     * @param accessUrl 접근 URL
     * @return 해당 URL의 시스템 장애 로그 목록
     */
    List<SystemTroubleLog> findByAccessUrl(String accessUrl);

    /**
     * 최근 시스템 장애 로그 조회 (최신순)
     * 
     * @return 최근 시스템 장애 로그 목록
     */
    @Query("SELECT stl FROM SystemTroubleLog stl ORDER BY stl.occurDate DESC")
    List<SystemTroubleLog> findRecentTroubleLogs();

    /**
     * 특정 프로그램의 특정 기간 장애 로그 조회
     * 
     * @param programId 프로그램 ID
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 조건의 시스템 장애 로그 목록
     */
    @Query("SELECT stl FROM SystemTroubleLog stl WHERE stl.programId = :programId AND stl.occurDate BETWEEN :startDate AND :endDate ORDER BY stl.occurDate DESC")
    List<SystemTroubleLog> findByProgramIdAndOccurDateBetween(@Param("programId") String programId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 특정 사용자의 특정 기간 장애 로그 조회
     * 
     * @param userId 사용자 ID
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 조건의 시스템 장애 로그 목록
     */
    @Query("SELECT stl FROM SystemTroubleLog stl WHERE stl.userId = :userId AND stl.occurDate BETWEEN :startDate AND :endDate ORDER BY stl.occurDate DESC")
    List<SystemTroubleLog> findByUserIdAndOccurDateBetween(@Param("userId") String userId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 일별 시스템 장애 통계 조회
     * 
     * @param occurDate 발생 날짜 (YYYYMMDD 형식)
     * @return 해당 날짜의 장애 통계 (장애수, 영향받은 사용자수)
     */
    @Query("SELECT COUNT(stl) as troubleCount, COUNT(DISTINCT stl.userId) as affectedUserCount FROM SystemTroubleLog stl WHERE stl.occurDate LIKE CONCAT(:occurDate, '%')")
    Object[] getDailyTroubleStats(@Param("occurDate") String occurDate);

    /**
     * 프로그램별 장애 통계 조회 (TOP N)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 프로그램별 장애 통계 (프로그램ID, 장애수)
     */
    @Query("SELECT stl.programId, COUNT(stl) as troubleCount FROM SystemTroubleLog stl WHERE stl.occurDate BETWEEN :startDate AND :endDate GROUP BY stl.programId ORDER BY troubleCount DESC")
    List<Object[]> getTopTroublesByProgram(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 오류 주제별 장애 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 오류 주제별 장애 통계 (오류주제, 장애수)
     */
    @Query("SELECT stl.errorSubject, COUNT(stl) as troubleCount FROM SystemTroubleLog stl WHERE stl.occurDate BETWEEN :startDate AND :endDate GROUP BY stl.errorSubject ORDER BY troubleCount DESC")
    List<Object[]> getTroubleStatsByErrorSubject(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * IP별 장애 통계 조회 (TOP N)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return IP별 장애 통계 (IP주소, 장애수)
     */
    @Query("SELECT stl.accessIp, COUNT(stl) as troubleCount FROM SystemTroubleLog stl WHERE stl.occurDate BETWEEN :startDate AND :endDate GROUP BY stl.accessIp ORDER BY troubleCount DESC")
    List<Object[]> getTopTroublesByIp(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 시간대별 장애 패턴 분석
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 시간대별 장애 통계 (시간, 장애수)
     */
    @Query("SELECT SUBSTRING(stl.occurDate, 9, 2) as hour, COUNT(stl) as troubleCount FROM SystemTroubleLog stl WHERE stl.occurDate BETWEEN :startDate AND :endDate GROUP BY SUBSTRING(stl.occurDate, 9, 2) ORDER BY hour")
    List<Object[]> getHourlyTroublePattern(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 특정 프로그램과 사용자 정보와 함께 장애 로그 조회 (Join Fetch)
     * 
     * @param programId 프로그램 ID
     * @return 프로그램과 사용자 정보가 포함된 장애 로그 목록
     */
    @Query("SELECT stl FROM SystemTroubleLog stl LEFT JOIN FETCH stl.program LEFT JOIN FETCH stl.user WHERE stl.programId = :programId ORDER BY stl.occurDate DESC")
    List<SystemTroubleLog> findByProgramIdWithDetails(@Param("programId") String programId);

    /**
     * 반복되는 장애 패턴 탐지 (동일한 오류가 단시간 내 반복 발생)
     * 
     * @param errorSubject 오류 주제
     * @param timeWindow 시간 범위 (분 단위)
     * @param threshold 임계값 (장애 횟수)
     * @return 반복되는 장애 패턴 목록
     */
    @Query("SELECT stl FROM SystemTroubleLog stl WHERE stl.errorSubject = :errorSubject AND stl.occurDate >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL :timeWindow MINUTE), '%Y%m%d%H%i%s') GROUP BY stl.errorSubject HAVING COUNT(*) >= :threshold")
    List<SystemTroubleLog> findRepeatingTroublePatterns(@Param("errorSubject") String errorSubject, @Param("timeWindow") int timeWindow, @Param("threshold") long threshold);

    /**
     * 월별 장애 트렌드 조회
     * 
     * @param year 연도
     * @return 월별 장애 통계 (월, 장애수)
     */
    @Query("SELECT SUBSTRING(stl.occurDate, 5, 2) as month, COUNT(stl) as troubleCount FROM SystemTroubleLog stl WHERE SUBSTRING(stl.occurDate, 1, 4) = :year GROUP BY SUBSTRING(stl.occurDate, 5, 2) ORDER BY month")
    List<Object[]> getMonthlyTroubleTrend(@Param("year") String year);

    /**
     * 특정 프로그램의 장애 횟수 조회
     * 
     * @param programId 프로그램 ID
     * @return 프로그램의 장애 횟수
     */
    @Query("SELECT COUNT(stl) FROM SystemTroubleLog stl WHERE stl.programId = :programId")
    long countByProgramId(@Param("programId") String programId);

    /**
     * 특정 사용자와 관련된 장애 횟수 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자 관련 장애 횟수
     */
    @Query("SELECT COUNT(stl) FROM SystemTroubleLog stl WHERE stl.userId = :userId")
    long countByUserId(@Param("userId") String userId);

    /**
     * 특정 오류 주제의 장애 횟수 조회
     * 
     * @param errorSubject 오류 주제
     * @return 해당 오류 주제의 장애 횟수
     */
    @Query("SELECT COUNT(stl) FROM SystemTroubleLog stl WHERE stl.errorSubject LIKE %:errorSubject%")
    long countByErrorSubjectContaining(@Param("errorSubject") String errorSubject);
} 