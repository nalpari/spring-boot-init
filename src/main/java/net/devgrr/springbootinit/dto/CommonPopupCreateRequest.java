package net.devgrr.springbootinit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.devgrr.springbootinit.entity.PopupPosition;
import net.devgrr.springbootinit.entity.PopupType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonPopupCreateRequest {

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @NotNull(message = "팝업 타입은 필수입니다")
    private PopupType popupType;

    private PopupPosition position;

    private Integer width;

    private Integer height;

    @NotNull(message = "시작일시는 필수입니다")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime displayStartDate;

    @NotNull(message = "종료일시는 필수입니다")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime displayEndDate;

    private Boolean isActive;

    private Integer displayOrder;

    private Boolean closeButtonEnabled;

    private Integer autoCloseDuration;

    private String linkUrl;

    private String targetPage;
}