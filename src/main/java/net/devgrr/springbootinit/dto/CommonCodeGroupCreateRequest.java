package net.devgrr.springbootinit.dto;

import lombok.Data;

@Data
public class CommonCodeGroupCreateRequest {
    private String groupCode;
    private String groupName;
    private String description;
    private String useYn;
    private Integer sortOrder;
}