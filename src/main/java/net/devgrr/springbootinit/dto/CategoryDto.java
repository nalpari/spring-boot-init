package net.devgrr.springbootinit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private Integer displayOrder;
    private Boolean isActive;
    private Long parentId;
    private String parentName;
    private List<CategoryDto> children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}