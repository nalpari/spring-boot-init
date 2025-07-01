package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Popup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 팝업 리포지토리 인터페이스
 * 
 * Popup 엔티티에 대한 데이터베이스 접근 기능을 제공하는 Repository 인터페이스입니다.
 * Spring Data JPA를 활용하여 팝업 관리 시스템의 다양한 요구사항을 지원하며,
 * 팝업 노출 조건, 기간 관리, 상태별 조회 등의 기능을 제공합니다.
 * 
 * 주요 기능:
 * - 팝업 기본 CRUD 연산
 * - 활성화 기간별 팝업 조회
 * - 팝업 타입별 분류 조회
 * - 사용 상태별 팝업 관리
 * - 팝업 노출 조건 검증
 * 
 * @Repository: Spring의 Repository 컴포넌트임을 나타내는 어노테이션
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface PopupRepository extends JpaRepository<Popup, String> {

    /**
     * 사용 여부에 따른 팝업 목록 조회
     * 
     * @param useYn 사용 여부 ('Y': 사용, 'N': 미사용)
     * @return 사용 여부에 해당하는 팝업 목록
     */
    List<Popup> findByUseYn(String useYn);

    /**
     * 사용 중인 팝업 목록을 정렬 순서로 조회
     * 
     * @param useYn 사용 여부
     * @return 사용 중인 팝업 목록 (정렬 순서대로)
     */
    List<Popup> findByUseYnOrderBySortOrder(String useYn);

    /**
     * 팝업 타입별 팝업 목록 조회
     * 
     * @param popupType 팝업 타입
     * @return 해당 타입의 팝업 목록
     */
    List<Popup> findByPopupType(String popupType);

    /**
     * 현재 시간 기준 활성화된 팝업 목록 조회
     * 
     * @param currentTime 현재 시간
     * @return 현재 시간에 활성화된 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.useYn = 'Y' " +
           "AND (p.startDate IS NULL OR p.startDate <= :currentTime) " +
           "AND (p.endDate IS NULL OR p.endDate >= :currentTime) " +
           "ORDER BY p.sortOrder")
    List<Popup> findActivePopups(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 특정 기간에 활성화된 팝업 목록 조회
     * 
     * @param startTime 기간 시작 시간
     * @param endTime 기간 종료 시간
     * @return 해당 기간에 활성화된 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.useYn = 'Y' " +
           "AND NOT (p.endDate < :startTime OR p.startDate > :endTime) " +
           "ORDER BY p.sortOrder")
    List<Popup> findPopupsActiveBetween(@Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);

    /**
     * 팝업명으로 검색 (부분 일치, 대소문자 구분 없음)
     * 
     * @param popupName 검색할 팝업명
     * @return 팝업명에 해당 문자열이 포함된 팝업 목록
     */
    List<Popup> findByPopupNameContainingIgnoreCase(String popupName);

    /**
     * 팝업명으로 검색 (페이징)
     * 
     * @param popupName 검색할 팝업명
     * @param pageable 페이징 정보
     * @return 팝업명에 해당 문자열이 포함된 팝업 목록 (페이징)
     */
    Page<Popup> findByPopupNameContainingIgnoreCase(String popupName, Pageable pageable);

    /**
     * 모달 팝업 여부로 조회
     * 
     * @param modalYn 모달 여부 ('Y': 모달, 'N': 일반)
     * @return 모달 여부에 해당하는 팝업 목록
     */
    List<Popup> findByModalYn(String modalYn);

    /**
     * 오늘 하루 보지 않기 기능 제공 팝업 조회
     * 
     * @param todayNotViewYn 오늘 하루 보지 않기 제공 여부
     * @return 해당 기능을 제공하는 팝업 목록
     */
    List<Popup> findByTodayNotViewYn(String todayNotViewYn);

    /**
     * 팝업 타입과 사용 여부로 조회
     * 
     * @param popupType 팝업 타입
     * @param useYn 사용 여부
     * @return 조건에 해당하는 팝업 목록
     */
    List<Popup> findByPopupTypeAndUseYn(String popupType, String useYn);

    /**
     * 팝업 ID 존재 여부 확인
     * 
     * @param popupId 팝업 ID
     * @return 존재 여부
     */
    boolean existsByPopupId(String popupId);

    /**
     * 팝업명 존재 여부 확인 (대소문자 구분 없음)
     * 
     * @param popupName 팝업명
     * @return 존재 여부
     */
    boolean existsByPopupNameIgnoreCase(String popupName);

    /**
     * 사용 중인 팝업 개수 조회
     * 
     * @return 사용 중인 팝업 개수
     */
    @Query("SELECT COUNT(p) FROM Popup p WHERE p.useYn = 'Y'")
    long countActivePopups();

    /**
     * 팝업 타입별 개수 조회
     * 
     * @param popupType 팝업 타입
     * @return 해당 타입의 팝업 개수
     */
    long countByPopupType(String popupType);

    /**
     * 현재 노출 중인 팝업 개수 조회
     * 
     * @param currentTime 현재 시간
     * @return 현재 노출 중인 팝업 개수
     */
    @Query("SELECT COUNT(p) FROM Popup p " +
           "WHERE p.useYn = 'Y' " +
           "AND (p.startDate IS NULL OR p.startDate <= :currentTime) " +
           "AND (p.endDate IS NULL OR p.endDate >= :currentTime)")
    long countCurrentlyActivePopups(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 만료된 팝업 목록 조회
     * 
     * @param currentTime 현재 시간
     * @return 만료된 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.useYn = 'Y' " +
           "AND p.endDate IS NOT NULL " +
           "AND p.endDate < :currentTime " +
           "ORDER BY p.endDate DESC")
    List<Popup> findExpiredPopups(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 앞으로 활성화될 팝업 목록 조회
     * 
     * @param currentTime 현재 시간
     * @return 앞으로 활성화될 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.useYn = 'Y' " +
           "AND p.startDate IS NOT NULL " +
           "AND p.startDate > :currentTime " +
           "ORDER BY p.startDate ASC")
    List<Popup> findUpcomingPopups(@Param("currentTime") LocalDateTime currentTime);

    /**
     * 팝업 크기별 조회 (너비, 높이 범위)
     * 
     * @param minWidth 최소 너비
     * @param maxWidth 최대 너비
     * @param minHeight 최소 높이
     * @param maxHeight 최대 높이
     * @return 크기 범위에 해당하는 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.popupWidth BETWEEN :minWidth AND :maxWidth " +
           "AND p.popupHeight BETWEEN :minHeight AND :maxHeight " +
           "AND p.useYn = 'Y'")
    List<Popup> findPopupsBySizeRange(@Param("minWidth") Integer minWidth,
                                    @Param("maxWidth") Integer maxWidth,
                                    @Param("minHeight") Integer minHeight,
                                    @Param("maxHeight") Integer maxHeight);

    /**
     * 등록자별 팝업 목록 조회
     * 
     * @param registerId 등록자 ID
     * @return 해당 등록자가 등록한 팝업 목록
     */
    List<Popup> findByRegisterId(String registerId);

    /**
     * 팝업 통계 정보 조회 (타입별 집계)
     * 
     * @return 팝업 타입별 통계 정보
     */
    @Query("SELECT p.popupType, " +
           "COUNT(*) as totalCount, " +
           "SUM(CASE WHEN p.useYn = 'Y' THEN 1 ELSE 0 END) as activeCount, " +
           "AVG(p.popupWidth) as avgWidth, " +
           "AVG(p.popupHeight) as avgHeight " +
           "FROM Popup p " +
           "GROUP BY p.popupType " +
           "ORDER BY totalCount DESC")
    List<Object[]> findPopupStatisticsByType();

    /**
     * 특정 기간에 등록된 팝업 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간에 등록된 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.registerDate BETWEEN :startDate AND :endDate " +
           "ORDER BY p.registerDate DESC")
    List<Popup> findPopupsRegisteredBetween(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

    /**
     * URL을 가진 팝업 목록 조회
     * 
     * @return URL이 설정된 팝업 목록
     */
    @Query("SELECT p FROM Popup p " +
           "WHERE p.popupUrl IS NOT NULL " +
           "AND p.popupUrl != '' " +
           "AND p.useYn = 'Y' " +
           "ORDER BY p.sortOrder")
    List<Popup> findPopupsWithUrl();
} 