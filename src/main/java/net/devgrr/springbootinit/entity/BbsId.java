package net.devgrr.springbootinit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * BBS 게시판 복합키 클래스
 * 
 * Bbs 엔티티의 복합 기본키를 나타내는 클래스입니다.
 * BBS ID와 공지사항 ID의 조합으로 고유성을 보장합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BbsId implements Serializable {

    /**
     * BBS ID
     * 게시물이 속한 게시판의 고유 식별자입니다.
     */
    private String bbsId;

    /**
     * 공지사항 ID
     * 게시물의 고유 식별자입니다.
     */
    private Long notiId;
} 