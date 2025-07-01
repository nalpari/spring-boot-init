package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 시퀀스 관리 엔티티
 * 
 * 시스템에서 사용되는 각종 시퀀스의 생성과 관리를 담당하는 엔티티입니다.
 * 테이블별로 고유한 시퀀스 번호를 관리합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "COMTECOPPSEQ")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComtecOppSeq {

    /**
     * 테이블명
     * 시퀀스가 적용될 테이블의 이름입니다.
     */
    @Id
    @Column(name = "TABLE_NAME", length = 20)
    private String tableName;

    /**
     * 다음 ID
     * 해당 테이블에서 다음에 생성될 고유 ID 값입니다.
     * 새로운 레코드가 생성될 때마다 이 값이 증가됩니다.
     */
    @Column(name = "NEXT_ID", precision = 30)
    private Long nextId;
} 