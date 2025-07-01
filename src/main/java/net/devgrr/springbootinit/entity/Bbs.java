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
 * BBS 게시판 엔티티
 * 
 * 게시판 시스템의 게시물 정보를 관리하는 엔티티입니다.
 * 게시물의 작성, 수정, 삭제 및 각종 메타데이터를 관리합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_BBS")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BbsId.class)
public class Bbs {

    /**
     * BBS ID
     * 게시물이 속한 게시판의 고유 식별자입니다.
     */
    @Id
    @Column(name = "BBS_ID", length = 10)
    private String bbsId;

    /**
     * 공지사항 ID
     * 게시물의 고유 식별자입니다.
     */
    @Id
    @Column(name = "NTI_ID", precision = 15)
    private Long notiId;

    /**
     * 공지 번호
     * 게시물의 번호입니다.
     */
    @Column(name = "NTI_NO", precision = 15)
    private Long notiNo;

    /**
     * 공지사항 제목
     * 게시물의 제목입니다.
     */
    @Column(name = "NTI_SJ", length = 2000)
    private String notiSubject;

    /**
     * 공지사항 내용
     * 게시물의 내용입니다.
     */
    @Column(name = "NTI_CN")
    private String notiContent;

    /**
     * 답변 여부
     * 해당 게시물이 답변인지 여부입니다.
     */
    @Column(name = "ANSWER_YN", length = 1)
    private String answerYn;

    /**
     * 부모 번호
     * 답변 게시물의 경우 원본 게시물의 번호입니다.
     */
    @Column(name = "PARENT_NO", precision = 15)
    private Long parentNo;

    /**
     * 답변 계층
     * 답변의 깊이를 나타내는 레벨입니다.
     */
    @Column(name = "ANSWER_LC", precision = 10)
    private Integer answerLevel;

    /**
     * 정렬 순서
     * 게시물의 정렬 순서입니다.
     */
    @Column(name = "SORT_ORDR", precision = 15)
    private Long sortOrder;

    /**
     * 조회수
     * 게시물의 조회 횟수입니다.
     */
    @Column(name = "RDCNT", precision = 10)
    private Integer readCount;

    /**
     * 사용 여부
     * 해당 게시물의 사용 여부입니다.
     */
    @Column(name = "USE_YN", length = 1)
    private String useYn;

    /**
     * 공지시작 여부
     * 공지사항 시작 기능 사용 여부입니다.
     */
    @Column(name = "NTCE_BGNDE", length = 8)
    private String noticeBgnDate;

    /**
     * 공지종료 여부
     * 공지사항 종료 기능 사용 여부입니다.
     */
    @Column(name = "NTCE_ENDDE", length = 8)
    private String noticeEndDate;

    /**
     * 공지 ID
     * 공지사항의 추가 식별자입니다.
     */
    @Column(name = "NTCR_ID", length = 20)
    private String noticerId;

    /**
     * 공지자명
     * 공지사항을 작성한 사용자의 이름입니다.
     */
    @Column(name = "NTCR_NM", length = 50)
    private String noticerName;

    /**
     * 첨부파일 ID
     * 게시물에 첨부된 파일의 ID입니다.
     */
    @Column(name = "ATCH_FILE_ID", length = 20)
    private String atchFileId;

    /**
     * 상위 공지 여부
     * 상위 공지사항인지 여부입니다.
     */
    @Column(name = "TOT_NOTI_YN", length = 1)
    private String totNotiYn;

    /**
     * 등록 ID
     * 해당 게시물을 등록한 사용자의 ID입니다.
     */
    @Column(name = "REGI_ID", length = 20)
    private String registerId;

    /**
     * 등록 일시
     * 게시물이 등록된 일시입니다.
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private LocalDateTime registerDate;

    /**
     * 수정 ID
     * 해당 게시물을 마지막으로 수정한 사용자의 ID입니다.
     */
    @Column(name = "UPDT_ID", length = 20)
    private String updateId;

    /**
     * 수정 일시
     * 게시물이 마지막으로 수정된 일시입니다.
     */
    @LastModifiedDate
    @Column(name = "UPDT_DT")
    private LocalDateTime updateDate;
} 