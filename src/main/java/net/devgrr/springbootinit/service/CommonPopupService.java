package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.CommonPopupCreateRequest;
import net.devgrr.springbootinit.dto.CommonPopupDto;
import net.devgrr.springbootinit.entity.CommonPopup;
import net.devgrr.springbootinit.entity.PopupPosition;
import net.devgrr.springbootinit.entity.PopupType;
import net.devgrr.springbootinit.exception.CommonPopupNotFoundException;
import net.devgrr.springbootinit.repository.CommonPopupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonPopupService {

    private final CommonPopupRepository commonPopupRepository;

    @Transactional(readOnly = true)
    public List<CommonPopupDto> getAllPopups() {
        return commonPopupRepository.findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CommonPopupDto> getAllPopups(Pageable pageable) {
        return commonPopupRepository.findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<CommonPopupDto> getActivePopups() {
        return commonPopupRepository.findActivePopupsAtDateTime(LocalDateTime.now())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommonPopupDto> getActivePopupsForPage(String targetPage) {
        return commonPopupRepository.findActivePopupsForPage(LocalDateTime.now(), targetPage)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommonPopupDto> getPopupsByType(PopupType popupType) {
        return commonPopupRepository.findByPopupTypeAndIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(popupType)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommonPopupDto getPopupById(Long id) {
        CommonPopup popup = commonPopupRepository.findById(id)
                .orElseThrow(() -> new CommonPopupNotFoundException(id));
        return convertToDto(popup);
    }

    @Transactional(readOnly = true)
    public List<CommonPopupDto> searchPopups(String keyword) {
        return commonPopupRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getActivePopupCount() {
        return commonPopupRepository.countActivePopups(LocalDateTime.now());
    }

    public CommonPopupDto createPopup(CommonPopupCreateRequest request) {
        validatePopupRequest(request);

        CommonPopup popup = CommonPopup.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .popupType(request.getPopupType())
                .position(request.getPosition() != null ? request.getPosition() : PopupPosition.CENTER)
                .width(request.getWidth())
                .height(request.getHeight())
                .displayStartDate(request.getDisplayStartDate())
                .displayEndDate(request.getDisplayEndDate())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .closeButtonEnabled(request.getCloseButtonEnabled() != null ? request.getCloseButtonEnabled() : true)
                .autoCloseDuration(request.getAutoCloseDuration())
                .linkUrl(request.getLinkUrl())
                .targetPage(request.getTargetPage())
                .build();

        CommonPopup savedPopup = commonPopupRepository.save(popup);
        return convertToDto(savedPopup);
    }

    public CommonPopupDto updatePopup(Long id, CommonPopupCreateRequest request) {
        CommonPopup popup = commonPopupRepository.findById(id)
                .orElseThrow(() -> new CommonPopupNotFoundException(id));

        validatePopupRequest(request);

        if (request.getTitle() != null) {
            popup.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            popup.setContent(request.getContent());
        }
        if (request.getPopupType() != null) {
            popup.setPopupType(request.getPopupType());
        }
        if (request.getPosition() != null) {
            popup.setPosition(request.getPosition());
        }
        if (request.getWidth() != null) {
            popup.setWidth(request.getWidth());
        }
        if (request.getHeight() != null) {
            popup.setHeight(request.getHeight());
        }
        if (request.getDisplayStartDate() != null) {
            popup.setDisplayStartDate(request.getDisplayStartDate());
        }
        if (request.getDisplayEndDate() != null) {
            popup.setDisplayEndDate(request.getDisplayEndDate());
        }
        if (request.getIsActive() != null) {
            popup.setIsActive(request.getIsActive());
        }
        if (request.getDisplayOrder() != null) {
            popup.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getCloseButtonEnabled() != null) {
            popup.setCloseButtonEnabled(request.getCloseButtonEnabled());
        }
        if (request.getAutoCloseDuration() != null) {
            popup.setAutoCloseDuration(request.getAutoCloseDuration());
        }
        if (request.getLinkUrl() != null) {
            popup.setLinkUrl(request.getLinkUrl());
        }
        if (request.getTargetPage() != null) {
            popup.setTargetPage(request.getTargetPage());
        }

        CommonPopup updatedPopup = commonPopupRepository.save(popup);
        return convertToDto(updatedPopup);
    }

    public void deletePopup(Long id) {
        CommonPopup popup = commonPopupRepository.findById(id)
                .orElseThrow(() -> new CommonPopupNotFoundException(id));

        popup.setIsActive(false);
        commonPopupRepository.save(popup);
    }

    private void validatePopupRequest(CommonPopupCreateRequest request) {
        if (request.getDisplayStartDate() != null && request.getDisplayEndDate() != null) {
            if (request.getDisplayStartDate().isAfter(request.getDisplayEndDate())) {
                throw new IllegalArgumentException("시작일시는 종료일시보다 이전이어야 합니다");
            }
        }

        if (request.getWidth() != null && request.getWidth() <= 0) {
            throw new IllegalArgumentException("가로 크기는 0보다 커야 합니다");
        }

        if (request.getHeight() != null && request.getHeight() <= 0) {
            throw new IllegalArgumentException("세로 크기는 0보다 커야 합니다");
        }

        if (request.getAutoCloseDuration() != null && request.getAutoCloseDuration() <= 0) {
            throw new IllegalArgumentException("자동 닫기 시간은 0보다 커야 합니다");
        }
    }

    private CommonPopupDto convertToDto(CommonPopup popup) {
        return CommonPopupDto.builder()
                .id(popup.getId())
                .title(popup.getTitle())
                .content(popup.getContent())
                .popupType(popup.getPopupType())
                .position(popup.getPosition())
                .width(popup.getWidth())
                .height(popup.getHeight())
                .displayStartDate(popup.getDisplayStartDate())
                .displayEndDate(popup.getDisplayEndDate())
                .isActive(popup.getIsActive())
                .displayOrder(popup.getDisplayOrder())
                .closeButtonEnabled(popup.getCloseButtonEnabled())
                .autoCloseDuration(popup.getAutoCloseDuration())
                .linkUrl(popup.getLinkUrl())
                .targetPage(popup.getTargetPage())
                .createdAt(popup.getCreatedAt())
                .updatedAt(popup.getUpdatedAt())
                .build();
    }
}