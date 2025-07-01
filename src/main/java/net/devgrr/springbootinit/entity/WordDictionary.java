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
 * 단어 사전 엔티티
 * 
 * 시스템에서 사용되는 다국어 단어 사전을 관리하는 엔티티입니다.
 * 한국어와 영어 단어 쌍을 저장하고 관리합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_WORD_DIC")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordDictionary {

    /**
     * 한국어 단어명
     * 사전에 등록된 한국어 단어입니다.
     */
    @Id
    @Column(name = "KOR_NM", length = 50)
    private String koreanName;

    /**
     * 영어 단어명
     * 한국어 단어에 대응하는 영어 단어입니다.
     */
    @Column(name = "ENG_NM", length = 50)
    private String englishName;

    /**
     * 출처
     * 단어의 출처나 참조 정보를 저장합니다.
     */
    @Column(name = "SRC", length = 10)
    private String source;

    /**
     * 영어 약어
     * 영어 단어의 줄임말이나 약어를 저장합니다.
     */
    @Column(name = "ENG_ABBR", length = 20)
    private String englishAbbreviation;

    /**
     * 단어 설명
     * 단어에 대한 상세한 설명이나 정의를 저장합니다.
     */
    @Column(name = "WORD_DESC", length = 4000)
    private String wordDescription;

    /**
     * 비고
     * 단어에 대한 추가적인 비고사항을 저장합니다.
     */
    @Column(name = "RMRK", length = 1000)
    private String remark;

    /**
     * 등록 ID
     * 해당 단어를 등록한 사용자의 ID입니다.
     */
    @Column(name = "REGI_ID", length = 20)
    private String registerId;

    /**
     * 등록 일시
     * 단어가 사전에 등록된 일시입니다.
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private LocalDateTime registerDate;

    /**
     * 수정 ID
     * 해당 단어를 마지막으로 수정한 사용자의 ID입니다.
     */
    @Column(name = "UPDT_ID", length = 20)
    private String updateId;

    /**
     * 수정 일시
     * 단어가 마지막으로 수정된 일시입니다.
     */
    @LastModifiedDate
    @Column(name = "UPDT_DT")
    private LocalDateTime updateDate;
} 