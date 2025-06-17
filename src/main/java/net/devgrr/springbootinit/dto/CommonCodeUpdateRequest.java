package net.devgrr.springbootinit.dto;

import lombok.Data;

@Data
public class CommonCodeUpdateRequest {
    private String codeName;
    private String codeValue;
    private String description;
    private String useYn;
    private Integer sortOrder;
}