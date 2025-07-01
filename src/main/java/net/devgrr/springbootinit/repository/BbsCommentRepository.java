package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.BbsComment;
import net.devgrr.springbootinit.entity.BbsCommentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BBS 댓글 Repository
 * 
 * BBS 댓글 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 게시물의 댓글 작성, 조회, 수정, 삭제 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface BbsCommentRepository extends JpaRepository<BbsComment, BbsCommentId> {

    /**
     * BBS ID와 공지사항 ID로 댓글 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물의 댓글 목록
     */
    List<BbsComment> findByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * BBS ID와 공지사항 ID로 댓글 목록을 댓글 번호 순으로 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @param pageable 페이징 정보
     * @return 해당 게시물의 댓글 목록 (댓글 번호 순)
     */
    @Query("SELECT c FROM BbsComment c WHERE c.bbsId = :bbsId AND c.notiId = :notiId ORDER BY c.commentNo ASC")
    Page<BbsComment> findByBbsIdAndNotiIdOrderByCommentNo(@Param("bbsId") String bbsId, 
                                                         @Param("notiId") Long notiId, 
                                                         Pageable pageable);

    /**
     * 작성자 ID로 댓글 목록을 조회합니다.
     * 
     * @param writeId 조회할 작성자 ID
     * @return 해당 작성자의 댓글 목록
     */
    List<BbsComment> findByWriteId(String writeId);

    /**
     * BBS ID로 댓글 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @return 해당 게시판의 모든 댓글 목록
     */
    List<BbsComment> findByBbsId(String bbsId);

    /**
     * 댓글 내용에 특정 키워드가 포함된 댓글들을 조회합니다.
     * 
     * @param keyword 검색할 키워드
     * @return 조건에 맞는 댓글 목록
     */
    @Query("SELECT c FROM BbsComment c WHERE c.commentContent LIKE %:keyword%")
    List<BbsComment> findByCommentContentContaining(@Param("keyword") String keyword);

    /**
     * 작성자명으로 댓글 목록을 조회합니다.
     * 
     * @param writeName 조회할 작성자명
     * @return 해당 작성자명의 댓글 목록
     */
    List<BbsComment> findByWriteName(String writeName);

    /**
     * 특정 기간 내에 작성된 댓글 목록을 조회합니다.
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 댓글 목록
     */
    @Query("SELECT c FROM BbsComment c WHERE c.registerDate BETWEEN :startDate AND :endDate ORDER BY c.registerDate DESC")
    List<BbsComment> findByRegisterDateBetween(@Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);

    /**
     * BBS ID와 공지사항 ID로 댓글 개수를 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 해당 게시물의 댓글 개수
     */
    Long countByBbsIdAndNotiId(String bbsId, Long notiId);

    /**
     * 작성자 ID로 댓글 개수를 조회합니다.
     * 
     * @param writeId 조회할 작성자 ID
     * @return 해당 작성자의 댓글 개수
     */
    Long countByWriteId(String writeId);

    /**
     * BBS ID와 공지사항 ID로 다음 댓글 번호를 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param notiId 조회할 공지사항 ID
     * @return 다음 댓글 번호
     */
    @Query("SELECT COALESCE(MAX(c.commentNo), 0) + 1 FROM BbsComment c WHERE c.bbsId = :bbsId AND c.notiId = :notiId")
    Long findNextCommentNo(@Param("bbsId") String bbsId, @Param("notiId") Long notiId);

    /**
     * 최근 댓글 목록을 조회합니다.
     * 
     * @param pageable 페이징 정보
     * @return 최근 댓글 목록
     */
    @Query("SELECT c FROM BbsComment c ORDER BY c.registerDate DESC")
    Page<BbsComment> findRecentComments(Pageable pageable);
} 