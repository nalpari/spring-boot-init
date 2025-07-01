package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BBS 관리 엔티티
 * 
 * 게시판(BBS) 시스템의 관리 설정을 담당하는 엔티티입니다.
 * 게시판의 생성, 수정, 삭제 및 각종 설정 정보를 관리합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_BBS_MNG")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbsManagement {

    /**
     * BBS ID
     * 게시판의 고유 식별자입니다.
     */
    @Id
    @Column(name = "BBS_ID", length = 10)
    private String bbsId;

    /**
     * BBS 명
     * 게시판의 이름입니다.
     */
    @Column(name = "BBS_NM", length = 50)
    private String bbsName;

    /**
     * BBS 설명
     * 게시판에 대한 상세한 설명입니다.
     */
    @Column(name = "BBS_DESC", length = 500)
    private String bbsDescription;

    /**
     * 브랜드 코드
     * 게시판이 속한 브랜드나 카테고리 코드입니다.
     */
    @Column(name = "BRND_CD", length = 2)
    private String brandCode;

    /**
     * BBS 유형 코드
     * 게시판의 유형을 구분하는 코드입니다.
     */
    @Column(name = "BBS_TYPE_CD", length = 5)
    private String bbsTypeCode;

    /**
     * 답글 가능 여부
     * 해당 게시판에서 답글 작성이 가능한지 여부입니다.
     */
    @Column(name = "REPLY_POSB_YN", length = 1)
    private String replyPossibleYn;

    /**
     * 댓글 가능 여부
     * 해당 게시판에서 댓글 작성이 가능한지 여부입니다.
     */
    @Column(name = "COMMENT_POSB_YN", length = 1)
    private String commentPossibleYn;

    /**
     * 파일 첨부 가능 여부
     * 해당 게시판에서 파일 첨부가 가능한지 여부입니다.
     */
    @Column(name = "FILE_ATCH_POSB_YN", length = 1)
    private String fileAttachPossibleYn;

    /**
     * 파일 첨부 개수
     * 한 게시물에 첨부할 수 있는 파일의 최대 개수입니다.
     */
    @Column(name = "FILE_ATCH_CNT")
    private Integer fileAttachCount;

    /**
     * 파일 첨부 크기
     * 첨부할 수 있는 파일의 최대 크기입니다. (바이트 단위)
     */
    @Column(name = "FILE_ATCH_SIZE", precision = 24)
    private Long fileAttachSize;

    /**
     * 공지사항 기간 사용 여부
     * 공지사항에 게시 기간을 설정할 수 있는지 여부입니다.
     */
    @Column(name = "NTI_TERM_USE_YN", length = 1)
    private String notiTermUseYn;

    /**
     * 메인 화면 게시 순서
     * 메인 화면에 노출될 때의 정렬 순서입니다.
     */
    @Column(name = "MAIN_DISP_ORDR")
    private Integer mainDisplayOrder;

    /**
     * 메인 화면 게시 개수
     * 메인 화면에 노출될 게시물의 개수입니다.
     */
    @Column(name = "MAIN_DISP_CNT")
    private Integer mainDisplayCount;

    /**
     * 사용 여부
     * 해당 게시판의 사용 여부입니다.
     */
    @Column(name = "USE_YN", length = 1)
    private String useYn;

    /**
     * 등록 ID
     * 해당 게시판을 등록한 사용자의 ID입니다.
     */
    @Column(name = "REGI_ID", length = 20)
    private String registerId;

    /**
     * 등록 일시
     * 게시판이 등록된 일시입니다.
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private LocalDateTime registerDate;

    /**
     * 수정 ID
     * 해당 게시판을 마지막으로 수정한 사용자의 ID입니다.
     */
    @Column(name = "UPDT_ID", length = 20)
    private String updateId;

    /**
     * 수정 일시
     * 게시판이 마지막으로 수정된 일시입니다.
     */
    @LastModifiedDate
    @Column(name = "UPDT_DT")
    private LocalDateTime updateDate;
} 