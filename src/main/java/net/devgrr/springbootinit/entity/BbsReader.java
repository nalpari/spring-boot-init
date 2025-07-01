package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BBS 읽기 관리 엔티티
 * 
 * 게시판 시스템에서 사용자의 게시물 읽기 상태를 관리하는 엔티티입니다.
 * 어떤 사용자가 어떤 게시물을 읽었는지 추적하는 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_BBS_READER")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(BbsReaderId.class)
public class BbsReader {

    /**
     * BBS ID
     * 게시물이 속한 게시판의 고유 식별자입니다.
     */
    @Id
    @Column(name = "BBS_ID", length = 10)
    private String bbsId;

    /**
     * 공지사항 ID
     * 읽기 상태를 추적할 게시물의 고유 식별자입니다.
     */
    @Id
    @Column(name = "NTI_ID", precision = 15)
    private Long notiId;

    /**
     * 사용자 ID
     * 게시물을 읽은 사용자의 ID입니다.
     */
    @Column(name = "USER_ID", length = 20)
    private String userId;

    /**
     * 등록 ID
     * 해당 읽기 정보를 등록한 사용자의 ID입니다.
     */
    @Column(name = "REGI_ID", length = 20)
    private String registerId;

    /**
     * 등록 일시
     * 읽기 정보가 등록된 일시입니다.
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private LocalDateTime registerDate;
} 