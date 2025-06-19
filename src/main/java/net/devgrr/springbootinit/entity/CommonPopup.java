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
@Table(name = "common_popups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CommonPopup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "popup_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private PopupType popupType;

    @Column(name = "position", length = 50)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PopupPosition position = PopupPosition.CENTER;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "display_start_date", nullable = false)
    private LocalDateTime displayStartDate;

    @Column(name = "display_end_date", nullable = false)
    private LocalDateTime displayEndDate;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(name = "close_button_enabled", nullable = false)
    @Builder.Default
    private Boolean closeButtonEnabled = true;

    @Column(name = "auto_close_duration")
    private Integer autoCloseDuration;

    @Column(name = "link_url", length = 500)
    private String linkUrl;

    @Column(name = "target_page", length = 100)
    private String targetPage;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isDisplayable() {
        LocalDateTime now = LocalDateTime.now();
        return isActive && 
               displayStartDate.isBefore(now) && 
               displayEndDate.isAfter(now);
    }
}