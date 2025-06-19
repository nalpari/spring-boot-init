package net.devgrr.springbootinit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.devgrr.springbootinit.entity.PopupPosition;
import net.devgrr.springbootinit.entity.PopupType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPopupDto {
    private Long id;
    private String title;
    private String content;
    private PopupType popupType;
    private PopupPosition position;
    private Integer width;
    private Integer height;
    private LocalDateTime displayStartDate;
    private LocalDateTime displayEndDate;
    private Boolean isActive;
    private Integer displayOrder;
    private Boolean closeButtonEnabled;
    private Integer autoCloseDuration;
    private String linkUrl;
    private String targetPage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}