package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Alarm;
import net.devgrr.springbootinit.entity.AlarmId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 알람 리포지토리 인터페이스
 * 
 * Alarm 엔티티에 대한 데이터베이스 접근 기능을 제공하는 Repository 인터페이스입니다.
 * Spring Data JPA를 활용하여 알람 시스템의 다양한 요구사항을 지원하며,
 * 알람 발송, 상태 관리, 통계 분석 등의 기능을 제공합니다.
 * 
 * 주요 기능:
 * - 알람 기본 CRUD 연산
 * - 사용자별 알람 관리
 * - 알람 타입별 분류 조회
 * - 읽음/미읽음 상태 관리
 * - 알람 발송 상태 추적
 * 
 * @Repository: Spring의 Repository 컴포넌트임을 나타내는 어노테이션
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface AlarmRepository extends JpaRepository<Alarm, AlarmId> {

    /**
     * 특정 사용자의 알람 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @return 해당 사용자의 알람 목록
     */
    List<Alarm> findByAlarmUserId(String alarmUserId);

    /**
     * 특정 사용자의 알람 목록 조회 (페이징)
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param pageable 페이징 정보
     * @return 해당 사용자의 알람 목록 (페이징)
     */
    Page<Alarm> findByAlarmUserId(String alarmUserId, Pageable pageable);

    /**
     * 알람 타입별 알람 목록 조회
     * 
     * @param alarmType 알람 타입
     * @return 해당 타입의 알람 목록
     */
    List<Alarm> findByAlarmType(String alarmType);

    /**
     * 읽음 여부별 알람 목록 조회
     * 
     * @param readYn 읽음 여부 ('Y': 읽음, 'N': 미읽음)
     * @return 해당 상태의 알람 목록
     */
    List<Alarm> findByReadYn(String readYn);

    /**
     * 발송 여부별 알람 목록 조회
     * 
     * @param sendYn 발송 여부 ('Y': 발송됨, 'N': 미발송)
     * @return 해당 상태의 알람 목록
     */
    List<Alarm> findBySendYn(String sendYn);

    /**
     * 특정 사용자의 미읽은 알람 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @return 해당 사용자의 미읽은 알람 목록
     */
    List<Alarm> findByAlarmUserIdAndReadYn(String alarmUserId, String readYn);

    /**
     * 특정 사용자의 특정 타입 알람 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param alarmType 알람 타입
     * @return 해당 조건의 알람 목록
     */
    List<Alarm> findByAlarmUserIdAndAlarmType(String alarmUserId, String alarmType);

    /**
     * 특정 기간의 알람 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 알람 목록
     */
    List<Alarm> findByAlarmDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 기간의 알람 목록 조회 (페이징)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @param pageable 페이징 정보
     * @return 해당 기간의 알람 목록 (페이징)
     */
    Page<Alarm> findByAlarmDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * 알람 제목으로 검색 (부분 일치)
     * 
     * @param alarmTitle 검색할 알람 제목
     * @return 제목에 해당 문자열이 포함된 알람 목록
     */
    List<Alarm> findByAlarmTitleContainingIgnoreCase(String alarmTitle);

    /**
     * 알람 내용으로 검색 (부분 일치)
     * 
     * @param alarmContent 검색할 알람 내용
     * @return 내용에 해당 문자열이 포함된 알람 목록
     */
    List<Alarm> findByAlarmContentContainingIgnoreCase(String alarmContent);

    /**
     * 특정 사용자의 미읽은 알람 개수 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @return 해당 사용자의 미읽은 알람 개수
     */
    long countByAlarmUserIdAndReadYn(String alarmUserId, String readYn);

    /**
     * 특정 사용자의 알람 개수 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @return 해당 사용자의 총 알람 개수
     */
    long countByAlarmUserId(String alarmUserId);

    /**
     * 알람 타입별 개수 조회
     * 
     * @param alarmType 알람 타입
     * @return 해당 타입의 알람 개수
     */
    long countByAlarmType(String alarmType);

    /**
     * 특정 기간의 알람 개수 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 알람 개수
     */
    long countByAlarmDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 사용자별 알람 통계 조회
     * 
     * @return 사용자별 알람 통계 정보
     */
    @Query("SELECT a.alarmUserId, " +
           "COUNT(*) as totalAlarms, " +
           "SUM(CASE WHEN a.readYn = 'Y' THEN 1 ELSE 0 END) as readAlarms, " +
           "SUM(CASE WHEN a.readYn = 'N' THEN 1 ELSE 0 END) as unreadAlarms, " +
           "SUM(CASE WHEN a.sendYn = 'Y' THEN 1 ELSE 0 END) as sentAlarms " +
           "FROM Alarm a " +
           "GROUP BY a.alarmUserId " +
           "ORDER BY totalAlarms DESC")
    List<Object[]> findUserAlarmStatistics();

    /**
     * 알람 타입별 통계 조회
     * 
     * @return 알람 타입별 통계 정보
     */
    @Query("SELECT a.alarmType, " +
           "COUNT(*) as totalAlarms, " +
           "COUNT(DISTINCT a.alarmUserId) as uniqueUsers, " +
           "SUM(CASE WHEN a.readYn = 'Y' THEN 1 ELSE 0 END) as readAlarms, " +
           "SUM(CASE WHEN a.sendYn = 'Y' THEN 1 ELSE 0 END) as sentAlarms " +
           "FROM Alarm a " +
           "WHERE a.alarmType IS NOT NULL " +
           "GROUP BY a.alarmType " +
           "ORDER BY totalAlarms DESC")
    List<Object[]> findAlarmTypeStatistics();

    /**
     * 일별 알람 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 일별 알람 통계 정보
     */
    @Query("SELECT DATE(a.alarmDate) as alarmDate, " +
           "COUNT(*) as totalAlarms, " +
           "COUNT(DISTINCT a.alarmUserId) as uniqueUsers, " +
           "SUM(CASE WHEN a.readYn = 'Y' THEN 1 ELSE 0 END) as readAlarms, " +
           "SUM(CASE WHEN a.sendYn = 'Y' THEN 1 ELSE 0 END) as sentAlarms " +
           "FROM Alarm a " +
           "WHERE a.alarmDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(a.alarmDate) " +
           "ORDER BY alarmDate DESC")
    List<Object[]> findDailyAlarmStatistics(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    /**
     * 시간대별 알람 통계 조회
     * 
     * @return 시간대별 알람 통계 정보
     */
    @Query("SELECT HOUR(a.alarmDate) as hour, " +
           "COUNT(*) as totalAlarms, " +
           "COUNT(DISTINCT a.alarmUserId) as uniqueUsers, " +
           "AVG(CASE WHEN a.readYn = 'Y' THEN 1.0 ELSE 0.0 END) as readRate " +
           "FROM Alarm a " +
           "GROUP BY HOUR(a.alarmDate) " +
           "ORDER BY hour")
    List<Object[]> findHourlyAlarmStatistics();

    /**
     * 특정 사용자의 최근 알람 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param limit 조회할 개수
     * @return 해당 사용자의 최근 알람 목록
     */
    @Query("SELECT a FROM Alarm a " +
           "WHERE a.alarmUserId = :alarmUserId " +
           "ORDER BY a.alarmDate DESC " +
           "LIMIT :limit")
    List<Alarm> findRecentAlarmsByUser(@Param("alarmUserId") String alarmUserId,
                                     @Param("limit") int limit);

    /**
     * 발송 실패한 알람 목록 조회
     * 
     * @return 발송 실패한 알람 목록
     */
    @Query("SELECT a FROM Alarm a " +
           "WHERE a.sendYn = 'N' " +
           "AND a.alarmDate < :currentTime " +
           "ORDER BY a.alarmDate ASC")
    List<Alarm> findFailedAlarms(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 오래된 미읽은 알람 목록 조회
     * 
     * @param thresholdDate 기준 일시
     * @return 기준 일시 이전의 미읽은 알람 목록
     */
    @Query("SELECT a FROM Alarm a " +
           "WHERE a.readYn = 'N' " +
           "AND a.alarmDate < :thresholdDate " +
           "ORDER BY a.alarmDate ASC")
    List<Alarm> findOldUnreadAlarms(@Param("thresholdDate") LocalDateTime thresholdDate);

    /**
     * 특정 사용자의 특정 기간 알람 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 조건의 알람 목록
     */
    @Query("SELECT a FROM Alarm a " +
           "WHERE a.alarmUserId = :alarmUserId " +
           "AND a.alarmDate BETWEEN :startDate AND :endDate " +
           "ORDER BY a.alarmDate DESC")
    List<Alarm> findUserAlarmsBetween(@Param("alarmUserId") String alarmUserId,
                                    @Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 읽음률이 낮은 알람 타입 조회
     * 
     * @param minAlarmCount 최소 알람 개수
     * @param maxReadRate 최대 읽음률 (0.0 ~ 1.0)
     * @return 읽음률이 낮은 알람 타입 목록
     */
    @Query("SELECT a.alarmType, " +
           "COUNT(*) as totalAlarms, " +
           "SUM(CASE WHEN a.readYn = 'Y' THEN 1 ELSE 0 END) as readAlarms, " +
           "(SUM(CASE WHEN a.readYn = 'Y' THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) as readRate " +
           "FROM Alarm a " +
           "WHERE a.alarmType IS NOT NULL " +
           "GROUP BY a.alarmType " +
           "HAVING COUNT(*) >= :minAlarmCount " +
           "AND (SUM(CASE WHEN a.readYn = 'Y' THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) <= :maxReadRate " +
           "ORDER BY readRate ASC")
    List<Object[]> findLowReadRateAlarmTypes(@Param("minAlarmCount") long minAlarmCount,
                                           @Param("maxReadRate") double maxReadRate);

    /**
     * 사용자별 미읽은 알람이 많은 사용자 조회
     * 
     * @param minUnreadCount 최소 미읽은 개수
     * @return 미읽은 알람이 많은 사용자 목록
     */
    @Query("SELECT a.alarmUserId, " +
           "COUNT(*) as totalAlarms, " +
           "SUM(CASE WHEN a.readYn = 'N' THEN 1 ELSE 0 END) as unreadAlarms, " +
           "MIN(a.alarmDate) as oldestUnreadDate " +
           "FROM Alarm a " +
           "WHERE a.readYn = 'N' " +
           "GROUP BY a.alarmUserId " +
           "HAVING SUM(CASE WHEN a.readYn = 'N' THEN 1 ELSE 0 END) >= :minUnreadCount " +
           "ORDER BY unreadAlarms DESC")
    List<Object[]> findUsersWithManyUnreadAlarms(@Param("minUnreadCount") long minUnreadCount);

    /**
     * 알람 발송 성공률 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 알람 발송 성공률 통계
     */
    @Query("SELECT COUNT(*) as totalAlarms, " +
           "SUM(CASE WHEN a.sendYn = 'Y' THEN 1 ELSE 0 END) as sentAlarms, " +
           "(SUM(CASE WHEN a.sendYn = 'Y' THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) as sendRate " +
           "FROM Alarm a " +
           "WHERE a.alarmDate BETWEEN :startDate AND :endDate")
    Object[] findAlarmSendSuccessRate(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);

    /**
     * 특정 사용자의 알람 읽음 처리
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param alarmSequence 알람 순번
     * @param readDate 읽음 일시
     * @return 업데이트된 행 수
     */
    @Query("UPDATE Alarm a SET a.readYn = 'Y', a.readDate = :readDate " +
           "WHERE a.alarmUserId = :alarmUserId AND a.alarmSequence = :alarmSequence")
    int markAsRead(@Param("alarmUserId") String alarmUserId,
                   @Param("alarmSequence") Integer alarmSequence,
                   @Param("readDate") LocalDateTime readDate);

    /**
     * 특정 사용자의 모든 알람 읽음 처리
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param readDate 읽음 일시
     * @return 업데이트된 행 수
     */
    @Query("UPDATE Alarm a SET a.readYn = 'Y', a.readDate = :readDate " +
           "WHERE a.alarmUserId = :alarmUserId AND a.readYn = 'N'")
    int markAllAsReadByUser(@Param("alarmUserId") String alarmUserId,
                            @Param("readDate") LocalDateTime readDate);
} 