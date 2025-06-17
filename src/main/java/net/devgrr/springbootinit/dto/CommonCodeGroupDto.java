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
public class CommonCodeGroupDto {
    private String groupCode;
    private String groupName;
    private String description;
    private String useYn;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommonCodeDto> commonCodes;
}