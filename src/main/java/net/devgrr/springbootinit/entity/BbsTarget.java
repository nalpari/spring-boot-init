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
 * BBS 대상 엔티티
 * 
 * 게시판 시스템에서 특정 게시물의 대상을 관리하는 엔티티입니다.
 * 게시물이 어떤 대상(사용자 그룹, 부서 등)에게 게시되는지를 관리합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_BBS_TARGET")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BbsTargetId.class)
public class BbsTarget {

    /**
     * BBS ID
     * 게시물이 속한 게시판의 고유 식별자입니다.
     */
    @Id
    @Column(name = "BBS_ID", length = 10)
    private String bbsId;

    /**
     * 공지사항 ID
     * 대상이 설정될 게시물의 고유 식별자입니다.
     */
    @Id
    @Column(name = "NTI_ID", precision = 15)
    private Long notiId;

    /**
     * BBS 유형 코드
     * 게시판의 유형을 구분하는 코드입니다.
     */
    @Column(name = "BBS_TYPE_CD", length = 5)
    private String bbsTypeCode;

    /**
     * 공지 대상
     * 게시물의 대상이 되는 그룹이나 부서의 코드입니다.
     */
    @Column(name = "NOTI_TARGET", length = 7)
    private String notiTarget;

    /**
     * 등록 ID
     * 해당 대상을 등록한 사용자의 ID입니다.
     */
    @Column(name = "REGI_ID", length = 20)
    private String registerId;

    /**
     * 등록 일시
     * 대상이 등록된 일시입니다.
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private LocalDateTime registerDate;
} 