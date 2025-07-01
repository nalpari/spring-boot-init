package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.ConnectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 연결 로그 Repository 인터페이스
 * 
 * 사용자의 시스템 연결(로그인/로그아웃) 이력에 대한 데이터 접근을 담당하는 Repository입니다.
 * 사용자 세션 관리, 보안 감사, 접속 통계 분석 등에 필요한 데이터를 제공합니다.
 * 
 * 주요 기능:
 * - 사용자별 연결 이력 조회
 * - 시간대별 접속 통계
 * - IP별 접속 이력 추적
 * - 세션 관리 지원
 * 
 * @Repository: Spring의 Repository 컴포넌트 등록
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface ConnectionLogRepository extends JpaRepository<ConnectionLog, Long> {

    /**
     * 특정 사용자의 연결 로그 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자의 연결 로그 목록
     */
    List<ConnectionLog> findByUserId(String userId);

    /**
     * 특정 사용자의 최근 연결 로그 조회 (최신순)
     * 
     * @param userId 사용자 ID
     * @param limit 조회할 로그 개수
     * @return 사용자의 최근 연결 로그 목록
     */
    @Query("SELECT cl FROM ConnectionLog cl WHERE cl.userId = :userId ORDER BY cl.connectDate DESC")
    List<ConnectionLog> findRecentConnectionsByUserId(@Param("userId") String userId);

    /**
     * 특정 IP 주소의 연결 로그 조회
     * 
     * @param connectIp 접속 IP 주소
     * @return 해당 IP의 연결 로그 목록
     */
    List<ConnectionLog> findByConnectIp(String connectIp);

    /**
     * 특정 기간의 연결 로그 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 연결 로그 목록
     */
    @Query("SELECT cl FROM ConnectionLog cl WHERE cl.connectDate BETWEEN :startDate AND :endDate ORDER BY cl.connectDate DESC")
    List<ConnectionLog> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * 특정 사용자의 특정 기간 연결 로그 조회
     * 
     * @param userId 사용자 ID
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 조건의 연결 로그 목록
     */
    @Query("SELECT cl FROM ConnectionLog cl WHERE cl.userId = :userId AND cl.connectDate BETWEEN :startDate AND :endDate ORDER BY cl.connectDate DESC")
    List<ConnectionLog> findByUserIdAndDateRange(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    /**
     * 브라우저별 연결 로그 조회
     * 
     * @param browser 브라우저 정보
     * @return 해당 브라우저의 연결 로그 목록
     */
    List<ConnectionLog> findByBrowser(String browser);

    /**
     * 특정 사용자의 마지막 로그인 정보 조회
     * 
     * @param userId 사용자 ID
     * @return 마지막 로그인 정보
     */
    @Query("SELECT cl FROM ConnectionLog cl WHERE cl.userId = :userId ORDER BY cl.connectDate DESC LIMIT 1")
    ConnectionLog findLastLoginByUserId(@Param("userId") String userId);

    /**
     * 특정 사용자의 오늘 접속 횟수 조회
     * 
     * @param userId 사용자 ID
     * @param today 오늘 날짜 (YYYYMMDD 형식)
     * @return 오늘 접속 횟수
     */
    @Query("SELECT COUNT(cl) FROM ConnectionLog cl WHERE cl.userId = :userId AND cl.connectDate = :today")
    long countTodayConnectionsByUserId(@Param("userId") String userId, @Param("today") String today);

    /**
     * 특정 IP의 오늘 접속 횟수 조회
     * 
     * @param connectIp 접속 IP 주소
     * @param today 오늘 날짜 (YYYYMMDD 형식)
     * @return 오늘 접속 횟수
     */
    @Query("SELECT COUNT(cl) FROM ConnectionLog cl WHERE cl.connectIp = :connectIp AND cl.connectDate = :today")
    long countTodayConnectionsByIp(@Param("connectIp") String connectIp, @Param("today") String today);

    /**
     * 일별 접속 통계 조회
     * 
     * @param date 조회할 날짜 (YYYYMMDD 형식)
     * @return 해당 날짜의 접속 통계 (사용자수, 접속수)
     */
    @Query("SELECT COUNT(DISTINCT cl.userId) as userCount, COUNT(cl) as connectionCount FROM ConnectionLog cl WHERE cl.connectDate = :date")
    Object[] getDailyConnectionStats(@Param("date") String date);

    /**
     * 시간대별 접속 통계 조회
     * 
     * @param date 조회할 날짜 (YYYYMMDD 형식)
     * @param hour 시간 (HH 형식)
     * @return 해당 시간대의 접속수
     */
    @Query("SELECT COUNT(cl) FROM ConnectionLog cl WHERE cl.connectDate = :date AND cl.connectTime LIKE :hour%")
    long getHourlyConnectionCount(@Param("date") String date, @Param("hour") String hour);

    /**
     * 특정 사용자의 접속 이력과 사용자 정보 함께 조회 (Join Fetch)
     * 
     * @param userId 사용자 ID
     * @return 사용자 정보가 포함된 연결 로그 목록
     */
    @Query("SELECT cl FROM ConnectionLog cl JOIN FETCH cl.user WHERE cl.userId = :userId ORDER BY cl.connectDate DESC")
    List<ConnectionLog> findByUserIdWithUser(@Param("userId") String userId);

    /**
     * 최근 활성 사용자 목록 조회 (최근 N일 이내 접속한 사용자)
     * 
     * @param days 최근 일수
     * @return 최근 활성 사용자 ID 목록
     */
    @Query("SELECT DISTINCT cl.userId FROM ConnectionLog cl WHERE cl.connectDate >= CURRENT_DATE - :days")
    List<String> findActiveUsersInRecentDays(@Param("days") int days);

    /**
     * 중복 접속 탐지 (동일 사용자가 동시에 여러 IP에서 접속)
     * 
     * @param userId 사용자 ID
     * @param timeWindow 시간 범위 (분 단위)
     * @return 중복 접속으로 의심되는 로그 목록
     */
    @Query("SELECT cl FROM ConnectionLog cl WHERE cl.userId = :userId AND cl.connectDate >= CURRENT_TIMESTAMP - INTERVAL :timeWindow MINUTE GROUP BY cl.connectIp HAVING COUNT(DISTINCT cl.connectIp) > 1")
    List<ConnectionLog> findSuspiciousMultipleConnections(@Param("userId") String userId, @Param("timeWindow") int timeWindow);

    /**
     * IP별 접속 통계 조회 (TOP N)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @param limit 조회할 상위 개수
     * @return IP별 접속 통계 (IP, 접속수)
     */
    @Query("SELECT cl.connectIp, COUNT(cl) as connectionCount FROM ConnectionLog cl WHERE cl.connectDate BETWEEN :startDate AND :endDate GROUP BY cl.connectIp ORDER BY connectionCount DESC")
    List<Object[]> getTopConnectionsByIp(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 사용자별 접속 통계 조회 (TOP N)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @param limit 조회할 상위 개수
     * @return 사용자별 접속 통계 (사용자ID, 접속수)
     */
    @Query("SELECT cl.userId, COUNT(cl) as connectionCount FROM ConnectionLog cl WHERE cl.connectDate BETWEEN :startDate AND :endDate GROUP BY cl.userId ORDER BY connectionCount DESC")
    List<Object[]> getTopConnectionsByUser(@Param("startDate") String startDate, @Param("endDate") String endDate);
} 