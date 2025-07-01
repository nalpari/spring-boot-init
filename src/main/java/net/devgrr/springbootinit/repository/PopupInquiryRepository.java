package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.PopupInquiry;
import net.devgrr.springbootinit.entity.PopupInquiryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 팝업 조회 이력 리포지토리 인터페이스
 * 
 * PopupInquiry 엔티티에 대한 데이터베이스 접근 기능을 제공하는 Repository 인터페이스입니다.
 * Spring Data JPA를 활용하여 팝업 조회 이력 관리, 통계 분석, 사용자 행동 추적 등의 
 * 기능을 지원하며, 팝업 성과 분석을 위한 다양한 쿼리 메서드를 제공합니다.
 * 
 * 주요 기능:
 * - 팝업 조회 이력 기본 CRUD 연산
 * - 사용자별 팝업 조회 이력 관리
 * - 팝업별 조회 통계 분석
 * - 디바이스별 조회 패턴 분석
 * - '오늘 하루 보지 않기' 기능 지원
 * 
 * @Repository: Spring의 Repository 컴포넌트임을 나타내는 어노테이션
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface PopupInquiryRepository extends JpaRepository<PopupInquiry, PopupInquiryId> {

    /**
     * 특정 팝업의 조회 이력 목록 조회
     * 
     * @param popupId 팝업 ID
     * @return 해당 팝업의 조회 이력 목록
     */
    List<PopupInquiry> findByPopupId(String popupId);

    /**
     * 특정 사용자의 팝업 조회 이력 목록 조회
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 팝업 조회 이력 목록
     */
    List<PopupInquiry> findByUserId(String userId);

    /**
     * 특정 사용자의 특정 팝업 조회 이력 목록 조회
     * 
     * @param userId 사용자 ID
     * @param popupId 팝업 ID
     * @return 해당 사용자의 해당 팝업 조회 이력 목록
     */
    List<PopupInquiry> findByUserIdAndPopupId(String userId, String popupId);

    /**
     * 조회 타입별 이력 목록 조회
     * 
     * @param inquiryType 조회 타입
     * @return 해당 조회 타입의 이력 목록
     */
    List<PopupInquiry> findByInquiryType(String inquiryType);

    /**
     * 디바이스 타입별 조회 이력 목록 조회
     * 
     * @param deviceType 디바이스 타입
     * @return 해당 디바이스 타입의 조회 이력 목록
     */
    List<PopupInquiry> findByDeviceType(String deviceType);

    /**
     * 특정 기간의 팝업 조회 이력 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 조회 이력 목록
     */
    List<PopupInquiry> findByInquiryDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 기간의 팝업 조회 이력 목록 조회 (페이징)
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @param pageable 페이징 정보
     * @return 해당 기간의 조회 이력 목록 (페이징)
     */
    Page<PopupInquiry> findByInquiryDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * '오늘 하루 보지 않기' 선택 이력 조회
     * 
     * @param todayNotViewYn 오늘 하루 보지 않기 여부
     * @return 해당 조건의 이력 목록
     */
    List<PopupInquiry> findByTodayNotViewYn(String todayNotViewYn);

    /**
     * 특정 IP 주소의 조회 이력 목록 조회
     * 
     * @param ipAddress IP 주소
     * @return 해당 IP 주소의 조회 이력 목록
     */
    List<PopupInquiry> findByIpAddress(String ipAddress);

    /**
     * 특정 팝업의 조회 횟수 계산
     * 
     * @param popupId 팝업 ID
     * @return 해당 팝업의 총 조회 횟수
     */
    long countByPopupId(String popupId);

    /**
     * 특정 사용자의 팝업 조회 횟수 계산
     * 
     * @param userId 사용자 ID
     * @return 해당 사용자의 총 팝업 조회 횟수
     */
    long countByUserId(String userId);

    /**
     * 특정 사용자의 특정 팝업 조회 횟수 계산
     * 
     * @param userId 사용자 ID
     * @param popupId 팝업 ID
     * @return 해당 사용자의 해당 팝업 조회 횟수
     */
    long countByUserIdAndPopupId(String userId, String popupId);

    /**
     * 특정 기간의 팝업 조회 횟수 계산
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 총 조회 횟수
     */
    long countByInquiryDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 팝업별 조회 통계 조회 (조회 횟수, 고유 사용자 수 등)
     * 
     * @return 팝업별 조회 통계 정보
     */
    @Query("SELECT pi.popupId, " +
           "COUNT(*) as totalViews, " +
           "COUNT(DISTINCT pi.userId) as uniqueUsers, " +
           "COUNT(DISTINCT pi.ipAddress) as uniqueIPs, " +
           "SUM(CASE WHEN pi.todayNotViewYn = 'Y' THEN 1 ELSE 0 END) as todayNotViewCount " +
           "FROM PopupInquiry pi " +
           "GROUP BY pi.popupId " +
           "ORDER BY totalViews DESC")
    List<Object[]> findPopupViewStatistics();

    /**
     * 디바이스별 조회 통계 조회
     * 
     * @return 디바이스별 조회 통계 정보
     */
    @Query("SELECT pi.deviceType, " +
           "COUNT(*) as totalViews, " +
           "COUNT(DISTINCT pi.userId) as uniqueUsers, " +
           "COUNT(DISTINCT pi.popupId) as uniquePopups " +
           "FROM PopupInquiry pi " +
           "WHERE pi.deviceType IS NOT NULL " +
           "GROUP BY pi.deviceType " +
           "ORDER BY totalViews DESC")
    List<Object[]> findDeviceTypeStatistics();

    /**
     * 일별 조회 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 일별 조회 통계 정보
     */
    @Query("SELECT DATE(pi.inquiryDate) as inquiryDate, " +
           "COUNT(*) as totalViews, " +
           "COUNT(DISTINCT pi.userId) as uniqueUsers, " +
           "COUNT(DISTINCT pi.popupId) as uniquePopups " +
           "FROM PopupInquiry pi " +
           "WHERE pi.inquiryDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(pi.inquiryDate) " +
           "ORDER BY inquiryDate DESC")
    List<Object[]> findDailyViewStatistics(@Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

    /**
     * 시간대별 조회 통계 조회
     * 
     * @return 시간대별 조회 통계 정보
     */
    @Query("SELECT HOUR(pi.inquiryDate) as hour, " +
           "COUNT(*) as totalViews, " +
           "COUNT(DISTINCT pi.userId) as uniqueUsers " +
           "FROM PopupInquiry pi " +
           "GROUP BY HOUR(pi.inquiryDate) " +
           "ORDER BY hour")
    List<Object[]> findHourlyViewStatistics();

    /**
     * 사용자별 팝업 조회 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 사용자별 조회 통계 정보
     */
    @Query("SELECT pi.userId, " +
           "COUNT(*) as totalViews, " +
           "COUNT(DISTINCT pi.popupId) as uniquePopups, " +
           "MAX(pi.inquiryDate) as lastViewDate " +
           "FROM PopupInquiry pi " +
           "WHERE pi.inquiryDate BETWEEN :startDate AND :endDate " +
           "AND pi.userId IS NOT NULL " +
           "GROUP BY pi.userId " +
           "ORDER BY totalViews DESC")
    List<Object[]> findUserViewStatistics(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    /**
     * 특정 팝업의 최근 조회 이력 조회
     * 
     * @param popupId 팝업 ID
     * @param limit 조회할 개수
     * @return 해당 팝업의 최근 조회 이력
     */
    @Query("SELECT pi FROM PopupInquiry pi " +
           "WHERE pi.popupId = :popupId " +
           "ORDER BY pi.inquiryDate DESC " +
           "LIMIT :limit")
    List<PopupInquiry> findRecentInquiriesByPopup(@Param("popupId") String popupId,
                                                @Param("limit") int limit);

    /**
     * 특정 사용자가 오늘 하루 보지 않기를 선택한 팝업 목록 조회
     * 
     * @param userId 사용자 ID
     * @param today 오늘 날짜
     * @return 오늘 하루 보지 않기를 선택한 팝업 ID 목록
     */
    @Query("SELECT DISTINCT pi.popupId FROM PopupInquiry pi " +
           "WHERE pi.userId = :userId " +
           "AND pi.todayNotViewYn = 'Y' " +
           "AND DATE(pi.inquiryDate) = DATE(:today)")
    List<String> findTodayNotViewPopupsByUser(@Param("userId") String userId,
                                            @Param("today") LocalDateTime today);

    /**
     * 브라우저별 조회 통계 조회
     * 
     * @return 브라우저별 조회 통계 정보
     */
    @Query("SELECT pi.browserInfo, " +
           "COUNT(*) as totalViews, " +
           "COUNT(DISTINCT pi.userId) as uniqueUsers " +
           "FROM PopupInquiry pi " +
           "WHERE pi.browserInfo IS NOT NULL " +
           "GROUP BY pi.browserInfo " +
           "ORDER BY totalViews DESC")
    List<Object[]> findBrowserStatistics();
} 