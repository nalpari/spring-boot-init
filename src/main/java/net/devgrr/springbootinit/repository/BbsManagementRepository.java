package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.BbsManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * BBS 관리 Repository
 * 
 * BBS 관리 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 게시판 설정 및 관리를 위한 CRUD 및 검색 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface BbsManagementRepository extends JpaRepository<BbsManagement, String> {

    /**
     * 사용 여부로 BBS 관리 목록을 조회합니다.
     * 
     * @param useYn 사용 여부 ('Y' 또는 'N')
     * @return 해당 조건의 BBS 관리 목록
     */
    List<BbsManagement> findByUseYn(String useYn);

    /**
     * BBS 유형 코드로 BBS 관리 목록을 조회합니다.
     * 
     * @param bbsTypeCode 조회할 BBS 유형 코드
     * @return 해당 유형의 BBS 관리 목록
     */
    List<BbsManagement> findByBbsTypeCode(String bbsTypeCode);

    /**
     * 브랜드 코드로 BBS 관리 목록을 조회합니다.
     * 
     * @param brandCode 조회할 브랜드 코드
     * @return 해당 브랜드의 BBS 관리 목록
     */
    List<BbsManagement> findByBrandCode(String brandCode);

    /**
     * BBS 명에 특정 문자열이 포함된 게시판들을 조회합니다.
     * 
     * @param bbsName 검색할 BBS 명 문자열
     * @return 조건에 맞는 BBS 관리 목록
     */
    List<BbsManagement> findByBbsNameContaining(String bbsName);

    /**
     * 답글 가능 여부로 BBS 관리 목록을 조회합니다.
     * 
     * @param replyPossibleYn 답글 가능 여부 ('Y' 또는 'N')
     * @return 해당 조건의 BBS 관리 목록
     */
    List<BbsManagement> findByReplyPossibleYn(String replyPossibleYn);

    /**
     * 댓글 가능 여부로 BBS 관리 목록을 조회합니다.
     * 
     * @param commentPossibleYn 댓글 가능 여부 ('Y' 또는 'N')
     * @return 해당 조건의 BBS 관리 목록
     */
    List<BbsManagement> findByCommentPossibleYn(String commentPossibleYn);

    /**
     * 파일 첨부 가능 여부로 BBS 관리 목록을 조회합니다.
     * 
     * @param fileAttachPossibleYn 파일 첨부 가능 여부 ('Y' 또는 'N')
     * @return 해당 조건의 BBS 관리 목록
     */
    List<BbsManagement> findByFileAttachPossibleYn(String fileAttachPossibleYn);

    /**
     * 메인 화면 게시 순서로 정렬된 사용 중인 BBS 목록을 조회합니다.
     * 
     * @return 메인 화면 게시 순서로 정렬된 BBS 목록
     */
    @Query("SELECT b FROM BbsManagement b WHERE b.useYn = 'Y' ORDER BY b.mainDisplayOrder ASC")
    List<BbsManagement> findActiveByMainDisplayOrder();

    /**
     * 브랜드 코드와 사용 여부로 BBS 관리 목록을 조회합니다.
     * 
     * @param brandCode 조회할 브랜드 코드
     * @param useYn 사용 여부
     * @param pageable 페이징 정보
     * @return 해당 조건의 BBS 관리 목록
     */
    Page<BbsManagement> findByBrandCodeAndUseYn(String brandCode, String useYn, Pageable pageable);

    /**
     * BBS ID의 존재 여부를 확인합니다.
     * 
     * @param bbsId 확인할 BBS ID
     * @return 존재 여부
     */
    boolean existsByBbsId(String bbsId);

    /**
     * 공지사항 기간 사용 여부로 BBS 관리 목록을 조회합니다.
     * 
     * @param notiTermUseYn 공지사항 기간 사용 여부 ('Y' 또는 'N')
     * @return 해당 조건의 BBS 관리 목록
     */
    List<BbsManagement> findByNotiTermUseYn(String notiTermUseYn);
} 