package net.devgrr.springbootinit.service;

import net.devgrr.springbootinit.dto.CommonPopupCreateRequest;
import net.devgrr.springbootinit.dto.CommonPopupDto;
import net.devgrr.springbootinit.entity.CommonPopup;
import net.devgrr.springbootinit.entity.PopupPosition;
import net.devgrr.springbootinit.entity.PopupType;
import net.devgrr.springbootinit.exception.CommonPopupNotFoundException;
import net.devgrr.springbootinit.repository.CommonPopupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommonPopupServiceTest {

    @Mock
    private CommonPopupRepository commonPopupRepository;

    @InjectMocks
    private CommonPopupService commonPopupService;

    private CommonPopup testPopup;
    private CommonPopupCreateRequest testCreateRequest;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        testPopup = CommonPopup.builder()
                .id(1L)
                .title("Test Popup")
                .content("Test Content")
                .popupType(PopupType.NOTICE)
                .position(PopupPosition.CENTER)
                .width(500)
                .height(300)
                .displayStartDate(now.minusDays(1))
                .displayEndDate(now.plusDays(1))
                .isActive(true)
                .displayOrder(1)
                .closeButtonEnabled(true)
                .autoCloseDuration(5000)
                .linkUrl("https://example.com")
                .targetPage("main")
                .createdAt(now)
                .updatedAt(now)
                .build();

        testCreateRequest = CommonPopupCreateRequest.builder()
                .title("New Popup")
                .content("New Content")
                .popupType(PopupType.EVENT)
                .position(PopupPosition.TOP_CENTER)
                .width(600)
                .height(400)
                .displayStartDate(now.plusHours(1))
                .displayEndDate(now.plusDays(2))
                .isActive(true)
                .displayOrder(2)
                .closeButtonEnabled(true)
                .autoCloseDuration(3000)
                .linkUrl("https://test.com")
                .targetPage("event")
                .build();
    }

    @Test
    void getAllPopups_shouldReturnAllActivePopups() {
        when(commonPopupRepository.findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc())
                .thenReturn(Arrays.asList(testPopup));

        List<CommonPopupDto> result = commonPopupService.getAllPopups();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Popup");
        verify(commonPopupRepository).findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc();
    }

    @Test
    void getAllPopupsWithPageable_shouldReturnPagedPopups() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CommonPopup> popupPage = new PageImpl<>(Arrays.asList(testPopup));
        when(commonPopupRepository.findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(pageable))
                .thenReturn(popupPage);

        Page<CommonPopupDto> result = commonPopupService.getAllPopups(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test Popup");
        verify(commonPopupRepository).findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(pageable);
    }

    @Test
    void getActivePopups_shouldReturnCurrentlyDisplayablePopups() {
        when(commonPopupRepository.findActivePopupsAtDateTime(any(LocalDateTime.class)))
                .thenReturn(Arrays.asList(testPopup));

        List<CommonPopupDto> result = commonPopupService.getActivePopups();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Popup");
        verify(commonPopupRepository).findActivePopupsAtDateTime(any(LocalDateTime.class));
    }

    @Test
    void getActivePopupsForPage_shouldReturnPopupsForSpecificPage() {
        when(commonPopupRepository.findActivePopupsForPage(any(LocalDateTime.class), eq("main")))
                .thenReturn(Arrays.asList(testPopup));

        List<CommonPopupDto> result = commonPopupService.getActivePopupsForPage("main");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTargetPage()).isEqualTo("main");
        verify(commonPopupRepository).findActivePopupsForPage(any(LocalDateTime.class), eq("main"));
    }

    @Test
    void getPopupsByType_shouldReturnPopupsOfSpecificType() {
        when(commonPopupRepository.findByPopupTypeAndIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(PopupType.NOTICE))
                .thenReturn(Arrays.asList(testPopup));

        List<CommonPopupDto> result = commonPopupService.getPopupsByType(PopupType.NOTICE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPopupType()).isEqualTo(PopupType.NOTICE);
        verify(commonPopupRepository).findByPopupTypeAndIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(PopupType.NOTICE);
    }

    @Test
    void getPopupById_shouldReturnPopup_whenPopupExists() {
        when(commonPopupRepository.findById(1L)).thenReturn(Optional.of(testPopup));

        CommonPopupDto result = commonPopupService.getPopupById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Test Popup");
        verify(commonPopupRepository).findById(1L);
    }

    @Test
    void getPopupById_shouldThrowException_whenPopupNotFound() {
        when(commonPopupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonPopupService.getPopupById(99L))
                .isInstanceOf(CommonPopupNotFoundException.class);
        verify(commonPopupRepository).findById(99L);
    }

    @Test
    void searchPopups_shouldReturnMatchingPopups() {
        when(commonPopupRepository.searchByKeyword("Test"))
                .thenReturn(Arrays.asList(testPopup));

        List<CommonPopupDto> result = commonPopupService.searchPopups("Test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Popup");
        verify(commonPopupRepository).searchByKeyword("Test");
    }

    @Test
    void getActivePopupCount_shouldReturnActiveCount() {
        when(commonPopupRepository.countActivePopups(any(LocalDateTime.class))).thenReturn(5L);

        long result = commonPopupService.getActivePopupCount();

        assertThat(result).isEqualTo(5L);
        verify(commonPopupRepository).countActivePopups(any(LocalDateTime.class));
    }

    @Test
    void createPopup_shouldCreateNewPopup_whenValidRequest() {
        when(commonPopupRepository.save(any(CommonPopup.class))).thenReturn(testPopup);

        CommonPopupDto result = commonPopupService.createPopup(testCreateRequest);

        assertThat(result).isNotNull();
        verify(commonPopupRepository).save(any(CommonPopup.class));
    }

    @Test
    void createPopup_shouldThrowException_whenStartDateAfterEndDate() {
        testCreateRequest.setDisplayStartDate(LocalDateTime.now().plusDays(2));
        testCreateRequest.setDisplayEndDate(LocalDateTime.now().plusDays(1));

        assertThatThrownBy(() -> commonPopupService.createPopup(testCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("시작일시는 종료일시보다 이전이어야 합니다");
        verify(commonPopupRepository, never()).save(any(CommonPopup.class));
    }

    @Test
    void createPopup_shouldThrowException_whenInvalidWidth() {
        testCreateRequest.setWidth(0);

        assertThatThrownBy(() -> commonPopupService.createPopup(testCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("가로 크기는 0보다 커야 합니다");
        verify(commonPopupRepository, never()).save(any(CommonPopup.class));
    }

    @Test
    void createPopup_shouldThrowException_whenInvalidHeight() {
        testCreateRequest.setHeight(-100);

        assertThatThrownBy(() -> commonPopupService.createPopup(testCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("세로 크기는 0보다 커야 합니다");
        verify(commonPopupRepository, never()).save(any(CommonPopup.class));
    }

    @Test
    void createPopup_shouldThrowException_whenInvalidAutoCloseDuration() {
        testCreateRequest.setAutoCloseDuration(0);

        assertThatThrownBy(() -> commonPopupService.createPopup(testCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자동 닫기 시간은 0보다 커야 합니다");
        verify(commonPopupRepository, never()).save(any(CommonPopup.class));
    }

    @Test
    void updatePopup_shouldUpdatePopup_whenValidRequest() {
        CommonPopupCreateRequest updateRequest = CommonPopupCreateRequest.builder()
                .title("Updated Popup")
                .content("Updated Content")
                .build();

        when(commonPopupRepository.findById(1L)).thenReturn(Optional.of(testPopup));
        when(commonPopupRepository.save(any(CommonPopup.class))).thenReturn(testPopup);

        CommonPopupDto result = commonPopupService.updatePopup(1L, updateRequest);

        assertThat(result).isNotNull();
        verify(commonPopupRepository).findById(1L);
        verify(commonPopupRepository).save(any(CommonPopup.class));
    }

    @Test
    void updatePopup_shouldThrowException_whenPopupNotFound() {
        CommonPopupCreateRequest updateRequest = CommonPopupCreateRequest.builder()
                .title("Updated Popup")
                .build();

        when(commonPopupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonPopupService.updatePopup(99L, updateRequest))
                .isInstanceOf(CommonPopupNotFoundException.class);
        verify(commonPopupRepository).findById(99L);
        verify(commonPopupRepository, never()).save(any(CommonPopup.class));
    }

    @Test
    void deletePopup_shouldSetIsActiveToFalse() {
        when(commonPopupRepository.findById(1L)).thenReturn(Optional.of(testPopup));
        when(commonPopupRepository.save(any(CommonPopup.class))).thenReturn(testPopup);

        commonPopupService.deletePopup(1L);

        verify(commonPopupRepository).findById(1L);
        verify(commonPopupRepository).save(any(CommonPopup.class));
    }

    @Test
    void deletePopup_shouldThrowException_whenPopupNotFound() {
        when(commonPopupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonPopupService.deletePopup(99L))
                .isInstanceOf(CommonPopupNotFoundException.class);
        verify(commonPopupRepository).findById(99L);
        verify(commonPopupRepository, never()).save(any(CommonPopup.class));
    }

    @Test
    void convertToDto_shouldMapAllFields() {
        when(commonPopupRepository.findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc())
                .thenReturn(Arrays.asList(testPopup));

        List<CommonPopupDto> popups = commonPopupService.getAllPopups();
        CommonPopupDto dto = popups.get(0);

        assertThat(dto.getId()).isEqualTo(testPopup.getId());
        assertThat(dto.getTitle()).isEqualTo(testPopup.getTitle());
        assertThat(dto.getContent()).isEqualTo(testPopup.getContent());
        assertThat(dto.getPopupType()).isEqualTo(testPopup.getPopupType());
        assertThat(dto.getPosition()).isEqualTo(testPopup.getPosition());
        assertThat(dto.getWidth()).isEqualTo(testPopup.getWidth());
        assertThat(dto.getHeight()).isEqualTo(testPopup.getHeight());
        assertThat(dto.getDisplayStartDate()).isEqualTo(testPopup.getDisplayStartDate());
        assertThat(dto.getDisplayEndDate()).isEqualTo(testPopup.getDisplayEndDate());
        assertThat(dto.getIsActive()).isEqualTo(testPopup.getIsActive());
        assertThat(dto.getDisplayOrder()).isEqualTo(testPopup.getDisplayOrder());
        assertThat(dto.getCloseButtonEnabled()).isEqualTo(testPopup.getCloseButtonEnabled());
        assertThat(dto.getAutoCloseDuration()).isEqualTo(testPopup.getAutoCloseDuration());
        assertThat(dto.getLinkUrl()).isEqualTo(testPopup.getLinkUrl());
        assertThat(dto.getTargetPage()).isEqualTo(testPopup.getTargetPage());
    }
}