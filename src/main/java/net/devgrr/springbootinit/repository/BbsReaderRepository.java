package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.BbsReader;
import net.devgrr.springbootinit.entity.BbsReaderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BBS 읽기 관리 Repository
 * 
 * BBS 읽기 관리 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 사용자의 게시물 읽기 상태 추적 및 관리 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface BbsReaderRepository extends JpaRepository<BbsReader, BbsReaderId> {

    /**
     * BBS ID와 공지사항 ID로 읽기 기록 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물의 읽기 기록 목록
     */
    List<BbsReader> findByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * 사용자 ID로 읽기 기록 목록을 조회합니다.
     * 
     * @param userId 조회할 사용자 ID
     * @return 해당 사용자의 읽기 기록 목록
     */
    List<BbsReader> findByUserId(String userId);

    /**
     * BBS ID로 읽기 기록 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @return 해당 게시판의 모든 읽기 기록 목록
     */
    List<BbsReader> findByBbsId(String bbsId);

    /**
     * BBS ID와 사용자 ID로 읽기 기록 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param userId 조회할 사용자 ID
     * @return 해당 조건의 읽기 기록 목록
     */
    List<BbsReader> findByBbsIdAndUserId(String bbsId, String userId);

    /**
     * 특정 사용자가 특정 게시물을 읽었는지 확인합니다.
     * 
     * @param bbsId 확인할 BBS ID
     * @param notiId 확인할 공지사항 ID
     * @param userId 확인할 사용자 ID
     * @return 읽기 여부
     */
    boolean existsByBbsIdAndNotiIdAndUserId(String bbsId, Long notiId, String userId);

    /**
     * BBS ID와 공지사항 ID로 읽은 사용자 수를 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물을 읽은 사용자 수
     */
    Long countByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * 사용자 ID로 읽은 게시물 수를 조회합니다.
     * 
     * @param userId 조회할 사용자 ID
     * @return 해당 사용자가 읽은 게시물 수
     */
    Long countByUserId(String userId);

    /**
     * 특정 기간 내에 읽은 기록 목록을 조회합니다.
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 읽기 기록 목록
     */
    @Query("SELECT r FROM BbsReader r WHERE r.registerDate BETWEEN :startDate AND :endDate ORDER BY r.registerDate DESC")
    List<BbsReader> findByRegisterDateBetween(@Param("startDate") LocalDateTime startDate, 
                                             @Param("endDate") LocalDateTime endDate);

    /**
     * 사용자별 최근 읽은 게시물 목록을 조회합니다.
     * 
     * @param userId 조회할 사용자 ID
     * @param limit 조회할 개수
     * @return 해당 사용자의 최근 읽은 게시물 목록
     */
    @Query("SELECT r FROM BbsReader r WHERE r.userId = :userId ORDER BY r.registerDate DESC LIMIT :limit")
    List<BbsReader> findRecentReadPostsByUser(@Param("userId") String userId, @Param("limit") int limit);

    /**
     * 게시물별 읽기 통계를 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @return 게시물별 읽기 횟수 통계
     */
    @Query("SELECT r.notiId, COUNT(r.userId) as readCount FROM BbsReader r WHERE r.bbsId = :bbsId GROUP BY r.notiId ORDER BY readCount DESC")
    List<Object[]> findReadStatisticsByBbsId(@Param("bbsId") String bbsId);

    /**
     * 특정 게시물을 읽은 사용자 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물을 읽은 사용자 목록
     */
    @Query("SELECT DISTINCT r.userId FROM BbsReader r WHERE r.bbsId = :bbsId AND r.notiId = :notiId ORDER BY r.registerDate DESC")
    List<String> findReadersByPost(@Param("bbsId") String bbsId, @Param("notiId") Long notiId);

    /**
     * BBS ID와 공지사항 ID로 해당 게시물의 모든 읽기 기록을 삭제합니다.
     * 
     * @param bbsId 삭제할 BBS ID
     * @param notiId 삭제할 공지사항 ID
     */
    void deleteByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * 사용자 ID로 해당 사용자의 모든 읽기 기록을 삭제합니다.
     * 
     * @param userId 삭제할 사용자 ID
     */
    void deleteByUserId(String userId);
} 