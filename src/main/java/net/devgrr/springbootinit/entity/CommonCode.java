package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "common_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code", referencedColumnName = "group_code", nullable = false)
    private CommonCodeGroup codeGroup;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "code_name", nullable = false, length = 100)
    private String codeName;

    @Column(name = "code_value", length = 200)
    private String codeValue;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "use_yn", nullable = false, length = 1)
    @Builder.Default
    private String useYn = "Y";

    @Column(name = "sort_order")
    private Integer sortOrder;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}