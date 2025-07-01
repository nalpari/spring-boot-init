package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.AlarmObject;
import net.devgrr.springbootinit.entity.AlarmObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 알람 객체 이력 리포지토리 인터페이스
 * 
 * AlarmObject 엔티티에 대한 데이터베이스 접근 기능을 제공하는 Repository 인터페이스입니다.
 * Spring Data JPA를 활용하여 알람 객체별 상세 이력 관리, 확인 상태 추적, 
 * 조직별 알람 관리 등의 기능을 지원하며, 알람 시스템의 세부 관리를 위한 
 * 다양한 쿼리 메서드를 제공합니다.
 * 
 * 주요 기능:
 * - 알람 객체 이력 기본 CRUD 연산
 * - 알람별 객체 이력 관리
 * - 사용자별 알람 객체 추적
 * - 확인 상태별 이력 조회
 * - 조직/부서별 알람 관리
 * 
 * @Repository: Spring의 Repository 컴포넌트임을 나타내는 어노테이션
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface AlarmObjectRepository extends JpaRepository<AlarmObject, AlarmObjectId> {

    /**
     * 특정 알람의 객체 이력 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param alarmSequence 알람 순번
     * @return 해당 알람의 객체 이력 목록
     */
    List<AlarmObject> findByAlarmUserIdAndAlarmSequence(String alarmUserId, Integer alarmSequence);

    /**
     * 특정 사용자의 알람 객체 이력 목록 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 알람 객체 이력 목록
     */
    List<AlarmObject> findByUserId(String userId);

    /**
     * 특정 사용자의 알람 객체 이력 목록 조회 (페이징)
     * 
     * @param userId 사용자 ID
     * @param pageable 페이징 정보
     * @return 해당 사용자의 알람 객체 이력 목록 (페이징)
     */
    Page<AlarmObject> findByUserId(String userId, Pageable pageable);

    /**
     * 확인 여부별 알람 객체 이력 목록 조회
     * 
     * @param confirmYn 확인 여부 ('Y': 확인됨, 'N': 미확인)
     * @return 해당 확인 상태의 알람 객체 이력 목록
     */
    List<AlarmObject> findByConfirmYn(String confirmYn);

    /**
     * 사업자/조직 구분별 알람 객체 이력 목록 조회
     * 
     * @param businessDivision 사업자/조직 구분
     * @return 해당 조직의 알람 객체 이력 목록
     */
    List<AlarmObject> findByBusinessDivision(String businessDivision);

    /**
     * 부서 구분별 알람 객체 이력 목록 조회
     * 
     * @param departmentDivision 부서 구분
     * @return 해당 부서의 알람 객체 이력 목록
     */
    List<AlarmObject> findByDepartmentDivision(String departmentDivision);

    /**
     * 사용자명으로 검색 (부분 일치)
     * 
     * @param userName 검색할 사용자명
     * @return 사용자명에 해당 문자열이 포함된 알람 객체 이력 목록
     */
    List<AlarmObject> findByUserNameContainingIgnoreCase(String userName);

    /**
     * 특정 기간의 알람 객체 이력 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 알람 객체 이력 목록
     */
    List<AlarmObject> findByRegisterDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 알람 사용자의 확인되지 않은 객체 이력 목록 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @return 해당 알람 사용자의 미확인 객체 이력 목록
     */
    List<AlarmObject> findByAlarmUserIdAndConfirmYn(String alarmUserId, String confirmYn);

    /**
     * 특정 사용자의 확인되지 않은 알람 객체 이력 목록 조회
     * 
     * @param userId 사용자 ID
     * @param confirmYn 확인 여부
     * @return 해당 사용자의 미확인 알람 객체 이력 목록
     */
    List<AlarmObject> findByUserIdAndConfirmYn(String userId, String confirmYn);

    /**
     * 조직과 부서로 알람 객체 이력 목록 조회
     * 
     * @param businessDivision 사업자/조직 구분
     * @param departmentDivision 부서 구분
     * @return 해당 조직/부서의 알람 객체 이력 목록
     */
    List<AlarmObject> findByBusinessDivisionAndDepartmentDivision(String businessDivision, String departmentDivision);

    /**
     * 특정 알람의 객체 이력 개수 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param alarmSequence 알람 순번
     * @return 해당 알람의 객체 이력 개수
     */
    long countByAlarmUserIdAndAlarmSequence(String alarmUserId, Integer alarmSequence);

    /**
     * 특정 사용자의 알람 객체 이력 개수 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 알람 객체 이력 개수
     */
    long countByUserId(String userId);

    /**
     * 확인 여부별 알람 객체 이력 개수 조회
     * 
     * @param confirmYn 확인 여부
     * @return 해당 확인 상태의 알람 객체 이력 개수
     */
    long countByConfirmYn(String confirmYn);

    /**
     * 특정 사용자의 미확인 알람 객체 개수 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 미확인 알람 객체 개수
     */
    long countByUserIdAndConfirmYn(String userId, String confirmYn);

    /**
     * 조직별 알람 객체 통계 조회
     * 
     * @return 조직별 알람 객체 통계 정보
     */
    @Query("SELECT ao.businessDivision, " +
           "COUNT(*) as totalObjects, " +
           "COUNT(DISTINCT ao.userId) as uniqueUsers, " +
           "SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) as confirmedObjects, " +
           "SUM(CASE WHEN ao.confirmYn = 'N' THEN 1 ELSE 0 END) as unconfirmedObjects " +
           "FROM AlarmObject ao " +
           "WHERE ao.businessDivision IS NOT NULL " +
           "GROUP BY ao.businessDivision " +
           "ORDER BY totalObjects DESC")
    List<Object[]> findBusinessDivisionStatistics();

    /**
     * 부서별 알람 객체 통계 조회
     * 
     * @return 부서별 알람 객체 통계 정보
     */
    @Query("SELECT ao.departmentDivision, " +
           "COUNT(*) as totalObjects, " +
           "COUNT(DISTINCT ao.userId) as uniqueUsers, " +
           "SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) as confirmedObjects, " +
           "AVG(CASE WHEN ao.confirmYn = 'Y' THEN 1.0 ELSE 0.0 END) as confirmRate " +
           "FROM AlarmObject ao " +
           "WHERE ao.departmentDivision IS NOT NULL " +
           "GROUP BY ao.departmentDivision " +
           "ORDER BY totalObjects DESC")
    List<Object[]> findDepartmentDivisionStatistics();

    /**
     * 사용자별 알람 객체 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 사용자별 알람 객체 통계 정보
     */
    @Query("SELECT ao.userId, ao.userName, " +
           "COUNT(*) as totalObjects, " +
           "SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) as confirmedObjects, " +
           "SUM(CASE WHEN ao.confirmYn = 'N' THEN 1 ELSE 0 END) as unconfirmedObjects, " +
           "MAX(ao.registerDate) as lastObjectDate " +
           "FROM AlarmObject ao " +
           "WHERE ao.registerDate BETWEEN :startDate AND :endDate " +
           "AND ao.userId IS NOT NULL " +
           "GROUP BY ao.userId, ao.userName " +
           "ORDER BY totalObjects DESC")
    List<Object[]> findUserObjectStatistics(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    /**
     * 일별 알람 객체 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 일별 알람 객체 통계 정보
     */
    @Query("SELECT DATE(ao.registerDate) as objectDate, " +
           "COUNT(*) as totalObjects, " +
           "COUNT(DISTINCT ao.userId) as uniqueUsers, " +
           "COUNT(DISTINCT CONCAT(ao.alarmUserId, '-', ao.alarmSequence)) as uniqueAlarms, " +
           "SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) as confirmedObjects " +
           "FROM AlarmObject ao " +
           "WHERE ao.registerDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(ao.registerDate) " +
           "ORDER BY objectDate DESC")
    List<Object[]> findDailyObjectStatistics(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    /**
     * 확인 지연 시간 통계 조회
     * 
     * @return 확인 지연 시간 통계 정보
     */
    @Query("SELECT AVG(TIMESTAMPDIFF(MINUTE, ao.registerDate, STR_TO_DATE(CONCAT(ao.confirmDate, ao.confirmTime), '%Y%m%d%H%i%s'))) as avgConfirmMinutes, " +
           "MIN(TIMESTAMPDIFF(MINUTE, ao.registerDate, STR_TO_DATE(CONCAT(ao.confirmDate, ao.confirmTime), '%Y%m%d%H%i%s'))) as minConfirmMinutes, " +
           "MAX(TIMESTAMPDIFF(MINUTE, ao.registerDate, STR_TO_DATE(CONCAT(ao.confirmDate, ao.confirmTime), '%Y%m%d%H%i%s'))) as maxConfirmMinutes " +
           "FROM AlarmObject ao " +
           "WHERE ao.confirmYn = 'Y' " +
           "AND ao.confirmDate IS NOT NULL " +
           "AND ao.confirmTime IS NOT NULL")
    Object[] findConfirmDelayStatistics();

    /**
     * 미확인 알람 객체 중 오래된 순으로 조회
     * 
     * @param limit 조회할 개수
     * @return 오래된 미확인 알람 객체 목록
     */
    @Query("SELECT ao FROM AlarmObject ao " +
           "WHERE ao.confirmYn = 'N' " +
           "ORDER BY ao.registerDate ASC " +
           "LIMIT :limit")
    List<AlarmObject> findOldestUnconfirmedObjects(@Param("limit") int limit);

    /**
     * 특정 알람의 확인률 조회
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param alarmSequence 알람 순번
     * @return 해당 알람의 확인률 정보
     */
    @Query("SELECT COUNT(*) as totalObjects, " +
           "SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) as confirmedObjects, " +
           "(SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) as confirmRate " +
           "FROM AlarmObject ao " +
           "WHERE ao.alarmUserId = :alarmUserId " +
           "AND ao.alarmSequence = :alarmSequence")
    Object[] findAlarmConfirmRate(@Param("alarmUserId") String alarmUserId,
                                @Param("alarmSequence") Integer alarmSequence);

    /**
     * 확인률이 낮은 조직 조회
     * 
     * @param minObjectCount 최소 객체 개수
     * @param maxConfirmRate 최대 확인률 (0.0 ~ 1.0)
     * @return 확인률이 낮은 조직 목록
     */
    @Query("SELECT ao.businessDivision, " +
           "COUNT(*) as totalObjects, " +
           "SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) as confirmedObjects, " +
           "(SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) as confirmRate " +
           "FROM AlarmObject ao " +
           "WHERE ao.businessDivision IS NOT NULL " +
           "GROUP BY ao.businessDivision " +
           "HAVING COUNT(*) >= :minObjectCount " +
           "AND (SUM(CASE WHEN ao.confirmYn = 'Y' THEN 1 ELSE 0 END) * 1.0 / COUNT(*)) <= :maxConfirmRate " +
           "ORDER BY confirmRate ASC")
    List<Object[]> findLowConfirmRateOrganizations(@Param("minObjectCount") long minObjectCount,
                                                 @Param("maxConfirmRate") double maxConfirmRate);

    /**
     * 특정 사용자의 최근 알람 객체 이력 조회
     * 
     * @param userId 사용자 ID
     * @param limit 조회할 개수
     * @return 해당 사용자의 최근 알람 객체 이력
     */
    @Query("SELECT ao FROM AlarmObject ao " +
           "WHERE ao.userId = :userId " +
           "ORDER BY ao.registerDate DESC " +
           "LIMIT :limit")
    List<AlarmObject> findRecentObjectsByUser(@Param("userId") String userId,
                                            @Param("limit") int limit);

    /**
     * 조직별 미확인 알람 객체가 많은 조직 조회
     * 
     * @param minUnconfirmedCount 최소 미확인 개수
     * @return 미확인 알람 객체가 많은 조직 목록
     */
    @Query("SELECT ao.businessDivision, " +
           "COUNT(*) as totalObjects, " +
           "SUM(CASE WHEN ao.confirmYn = 'N' THEN 1 ELSE 0 END) as unconfirmedObjects, " +
           "MIN(CASE WHEN ao.confirmYn = 'N' THEN ao.registerDate END) as oldestUnconfirmedDate " +
           "FROM AlarmObject ao " +
           "WHERE ao.businessDivision IS NOT NULL " +
           "GROUP BY ao.businessDivision " +
           "HAVING SUM(CASE WHEN ao.confirmYn = 'N' THEN 1 ELSE 0 END) >= :minUnconfirmedCount " +
           "ORDER BY unconfirmedObjects DESC")
    List<Object[]> findOrganizationsWithManyUnconfirmedObjects(@Param("minUnconfirmedCount") long minUnconfirmedCount);

    /**
     * 등록자별 알람 객체 이력 목록 조회
     * 
     * @param registerId 등록자 ID
     * @return 해당 등록자가 등록한 알람 객체 이력 목록
     */
    List<AlarmObject> findByRegisterId(String registerId);

    /**
     * 알람 객체 확인 처리
     * 
     * @param alarmUserId 알람 사용자 ID
     * @param alarmSequence 알람 순번
     * @param userId 사용자 ID
     * @param confirmDate 확인 일자
     * @param confirmTime 확인 시간
     * @return 업데이트된 행 수
     */
    @Query("UPDATE AlarmObject ao SET ao.confirmYn = 'Y', ao.confirmDate = :confirmDate, ao.confirmTime = :confirmTime " +
           "WHERE ao.alarmUserId = :alarmUserId AND ao.alarmSequence = :alarmSequence AND ao.userId = :userId")
    int markAsConfirmed(@Param("alarmUserId") String alarmUserId,
                        @Param("alarmSequence") Integer alarmSequence,
                        @Param("userId") String userId,
                        @Param("confirmDate") String confirmDate,
                        @Param("confirmTime") String confirmTime);
} 