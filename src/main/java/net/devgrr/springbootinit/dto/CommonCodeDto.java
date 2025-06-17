package net.devgrr.springbootinit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeDto {
    private Long id;
    private String groupCode;
    private String code;
    private String codeName;
    private String codeValue;
    private String description;
    private String useYn;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}