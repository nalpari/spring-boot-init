package net.devgrr.springbootinit.dto;

import lombok.Data;

@Data
public class CategoryCreateRequest {
    private String name;
    private String description;
    private Integer displayOrder;
    private Boolean isActive;
    private Long parentId;
}