package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ID 생성 관리 엔티티
 * 
 * 시스템에서 사용되는 각종 ID의 생성과 관리를 담당하는 엔티티입니다.
 * 테이블별 또는 유형별로 순차적인 ID 생성을 위해 사용됩니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "ID_GENERATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdGeneration {

    /**
     * 테이블명 또는 ID 유형
     * ID 생성이 필요한 테이블이나 기능의 이름을 저장합니다.
     */
    @Id
    @Column(name = "TABLE_NAME", length = 50)
    private String tableName;

    /**
     * 다음 ID 값
     * 해당 테이블이나 유형에서 다음에 생성될 ID 값을 저장합니다.
     * 새로운 ID가 필요할 때마다 이 값이 증가됩니다.
     */
    @Column(name = "NEXT_ID", nullable = false)
    private Long nextId;
} 