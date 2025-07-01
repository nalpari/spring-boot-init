package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Bbs;
import net.devgrr.springbootinit.entity.BbsId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * BBS 게시판 Repository
 * 
 * BBS 게시판 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 게시물의 CRUD 및 검색, 정렬 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface BbsRepository extends JpaRepository<Bbs, BbsId> {

    /**
     * BBS ID로 게시물 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @return 해당 게시판의 게시물 목록
     */
    List<Bbs> findByBbsId(String bbsId);

    /**
     * BBS ID와 사용 여부로 게시물 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param useYn 사용 여부
     * @param pageable 페이징 정보
     * @return 해당 조건의 게시물 목록
     */
    Page<Bbs> findByBbsIdAndUseYn(String bbsId, String useYn, Pageable pageable);

    /**
     * 공지자 ID로 게시물 목록을 조회합니다.
     * 
     * @param noticerId 조회할 공지자 ID
     * @return 해당 공지자의 게시물 목록
     */
    List<Bbs> findByNoticerId(String noticerId);

    /**
     * 제목에 특정 키워드가 포함된 게시물들을 조회합니다.
     * 
     * @param keyword 검색할 키워드
     * @return 조건에 맞는 게시물 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.notiSubject LIKE %:keyword%")
    List<Bbs> findByNotiSubjectContaining(@Param("keyword") String keyword);

    /**
     * 내용에 특정 키워드가 포함된 게시물들을 조회합니다.
     * 
     * @param keyword 검색할 키워드
     * @return 조건에 맞는 게시물 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.notiContent LIKE %:keyword%")
    List<Bbs> findByNotiContentContaining(@Param("keyword") String keyword);

    /**
     * 제목 또는 내용에 키워드가 포함된 게시물들을 조회합니다.
     * 
     * @param keyword 검색할 키워드
     * @param pageable 페이징 정보
     * @return 조건에 맞는 게시물 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.notiSubject LIKE %:keyword% OR b.notiContent LIKE %:keyword%")
    Page<Bbs> findByTitleOrContentContaining(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 상위 공지 여부로 게시물 목록을 조회합니다.
     * 
     * @param totNotiYn 상위 공지 여부
     * @return 해당 조건의 게시물 목록
     */
    List<Bbs> findByTotNotiYn(String totNotiYn);

    /**
     * 답변 여부로 게시물 목록을 조회합니다.
     * 
     * @param answerYn 답변 여부
     * @return 해당 조건의 게시물 목록
     */
    List<Bbs> findByAnswerYn(String answerYn);

    /**
     * 부모 번호로 답변 게시물 목록을 조회합니다.
     * 
     * @param parentNo 부모 게시물 번호
     * @return 해당 부모의 답변 게시물 목록
     */
    List<Bbs> findByParentNo(Long parentNo);

    /**
     * BBS ID와 정렬 순서로 게시물 목록을 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @param pageable 페이징 정보
     * @return 정렬 순서로 정렬된 게시물 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.bbsId = :bbsId AND b.useYn = 'Y' ORDER BY b.sortOrder ASC, b.registerDate DESC")
    Page<Bbs> findByBbsIdOrderBySortOrder(@Param("bbsId") String bbsId, Pageable pageable);

    /**
     * 조회수를 증가시킵니다.
     * 
     * @param bbsId 게시판 ID
     * @param notiId 공지사항 ID
     * @return 업데이트된 레코드 수
     */
    @Modifying
    @Query("UPDATE Bbs b SET b.readCount = b.readCount + 1 WHERE b.bbsId = :bbsId AND b.notiId = :notiId")
    int increaseReadCount(@Param("bbsId") String bbsId, @Param("notiId") Long notiId);

    /**
     * 특정 기간 내에 작성된 게시물 목록을 조회합니다.
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간의 게시물 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.registerDate BETWEEN :startDate AND :endDate ORDER BY b.registerDate DESC")
    List<Bbs> findByRegisterDateBetween(@Param("startDate") LocalDateTime startDate, 
                                       @Param("endDate") LocalDateTime endDate);

    /**
     * 공지 기간이 현재 유효한 공지사항 목록을 조회합니다.
     * 
     * @param currentDate 현재 일시
     * @return 현재 유효한 공지사항 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.noticeBgnDate <= :currentDate AND b.noticeEndDate >= :currentDate AND b.useYn = 'Y'")
    List<Bbs> findActiveNotices(@Param("currentDate") String currentDate);

    /**
     * BBS ID와 공지사항 ID로 다음 정렬 순서를 조회합니다.
     * 
     * @param bbsId 조회할 BBS ID
     * @return 다음 정렬 순서
     */
    @Query("SELECT COALESCE(MAX(b.sortOrder), 0) + 1 FROM Bbs b WHERE b.bbsId = :bbsId")
    Long findNextSortOrder(@Param("bbsId") String bbsId);

    /**
     * 첨부파일 ID로 게시물을 조회합니다.
     * 
     * @param atchFileId 첨부파일 ID
     * @return 해당 첨부파일을 가진 게시물 목록
     */
    List<Bbs> findByAtchFileId(String atchFileId);

    /**
     * 최근 인기 게시물을 조회합니다 (조회수 기준).
     * 
     * @param pageable 페이징 정보
     * @return 조회수 순으로 정렬된 게시물 목록
     */
    @Query("SELECT b FROM Bbs b WHERE b.useYn = 'Y' ORDER BY b.readCount DESC, b.registerDate DESC")
    Page<Bbs> findPopularPosts(Pageable pageable);
} 