package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.BbsTarget;
import net.devgrr.springbootinit.entity.BbsTargetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BBS 대상 Repository
 * 
 * BBS 대상 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 게시물의 대상 그룹 관리를 위한 CRUD 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface BbsTargetRepository extends JpaRepository<BbsTarget, BbsTargetId> {

    /**
     * BBS ID와 공지사항 ID로 대상 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물의 대상 목록
     */
    List<BbsTarget> findByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * BBS ID로 대상 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @return 해당 게시판의 모든 대상 목록
     */
    List<BbsTarget> findByBbsId(String bbsId);

    /**
     * 공지 대상으로 대상 목록을 조회합니다.
     * 
     * @param notiTarget 조회할 공지 대상
     * @return 해당 대상의 게시물 목록
     */
    List<BbsTarget> findByNotiTarget(String notiTarget);

    /**
     * BBS 유형 코드로 대상 목록을 조회합니다.
     * 
     * @param bbsTypeCode 조회할 BBS 유형 코드
     * @return 해당 유형의 대상 목록
     */
    List<BbsTarget> findByBbsTypeCode(String bbsTypeCode);

    /**
     * BBS ID와 공지 대상으로 대상 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiTarget 조회할 공지 대상
     * @return 조건에 맞는 대상 목록
     */
    List<BbsTarget> findByBbsIdAndNotiTarget(String bbsId, String notiTarget);

    /**
     * BBS 유형 코드와 공지 대상으로 대상 목록을 조회합니다.
     * 
     * @param bbsTypeCode 조회할 BBS 유형 코드
     * @param notiTarget 조회할 공지 대상
     * @return 조건에 맞는 대상 목록
     */
    List<BbsTarget> findByBbsTypeCodeAndNotiTarget(String bbsTypeCode, String notiTarget);

    /**
     * 특정 공지 대상에 대한 게시물 개수를 조회합니다.
     * 
     * @param notiTarget 조회할 공지 대상
     * @return 해당 대상의 게시물 개수
     */
    Long countByNotiTarget(String notiTarget);

    /**
     * BBS ID와 공지사항 ID로 대상 개수를 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물의 대상 개수
     */
    Long countByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * 특정 대상이 접근 가능한 게시물 목록을 조회합니다.
     * 
     * @param notiTarget 조회할 공지 대상
     * @return 해당 대상이 접근 가능한 게시물의 BBS ID와 공지사항 ID 목록
     */
    @Query("SELECT t FROM BbsTarget t WHERE t.notiTarget = :notiTarget ORDER BY t.registerDate DESC")
    List<BbsTarget> findAccessiblePostsByTarget(@Param("notiTarget") String notiTarget);

    /**
     * BBS ID와 공지사항 ID로 대상 존재 여부를 확인합니다.
     * 
     * @param bbsId 확인할 BBS ID
     * @param notiId 확인할 공지사항 ID
     * @return 대상 존재 여부
     */
    boolean existsByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * 특정 공지 대상과 BBS ID 조합의 존재 여부를 확인합니다.
     * 
     * @param bbsId 확인할 BBS ID
     * @param notiTarget 확인할 공지 대상
     * @return 조합 존재 여부
     */
    boolean existsByBbsIdAndNotiTarget(String bbsId, String notiTarget);

    /**
     * BBS ID와 공지사항 ID로 해당 게시물의 모든 대상을 삭제합니다.
     * 
     * @param bbsId 삭제할 BBS ID
     * @param notiId 삭제할 공지사항 ID
     */
    void deleteByBbsIdAndNotiId(String bbsId, Long notiId);
} 