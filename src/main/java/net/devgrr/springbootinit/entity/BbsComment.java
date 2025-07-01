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
 * BBS 댓글 엔티티
 * 
 * 게시판 시스템의 댓글 정보를 관리하는 엔티티입니다.
 * 게시물에 대한 댓글 작성, 수정, 삭제 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_BBS_COMMENT")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BbsCommentId.class)
public class BbsComment {

    /**
     * BBS ID
     * 댓글이 속한 게시판의 고유 식별자입니다.
     */
    @Id
    @Column(name = "BBS_ID", length = 10)
    private String bbsId;

    /**
     * 공지사항 ID
     * 댓글이 속한 게시물의 고유 식별자입니다.
     */
    @Id
    @Column(name = "NTI_ID", precision = 15)
    private Long notiId;

    /**
     * 댓글 번호
     * 해당 게시물 내에서의 댓글 순서번호입니다.
     */
    @Id
    @Column(name = "COMMENT_NO", precision = 20)
    private Long commentNo;

    /**
     * 작성자 ID
     * 댓글을 작성한 사용자의 ID입니다.
     */
    @Column(name = "WRITE_ID", length = 20)
    private String writeId;

    /**
     * 작성자명
     * 댓글을 작성한 사용자의 이름입니다.
     */
    @Column(name = "WRITE_NM", length = 50)
    private String writeName;

    /**
     * 댓글 내용
     * 작성된 댓글의 내용입니다.
     */
    @Column(name = "COMMENT_CN", length = 4000)
    private String commentContent;

    /**
     * 등록 ID
     * 해당 댓글을 등록한 사용자의 ID입니다.
     */
    @Column(name = "REGI_ID", length = 20)
    private String registerId;

    /**
     * 등록 일시
     * 댓글이 등록된 일시입니다.
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private LocalDateTime registerDate;
} 