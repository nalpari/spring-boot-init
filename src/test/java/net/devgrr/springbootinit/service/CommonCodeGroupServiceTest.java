package net.devgrr.springbootinit.service;

import net.devgrr.springbootinit.dto.CommonCodeGroupCreateRequest;
import net.devgrr.springbootinit.dto.CommonCodeGroupDto;
import net.devgrr.springbootinit.entity.CommonCodeGroup;
import net.devgrr.springbootinit.exception.CommonCodeAlreadyExistsException;
import net.devgrr.springbootinit.exception.CommonCodeGroupNotFoundException;
import net.devgrr.springbootinit.repository.CommonCodeGroupRepository;
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
class CommonCodeGroupServiceTest {

    @Mock
    private CommonCodeGroupRepository commonCodeGroupRepository;

    @InjectMocks
    private CommonCodeGroupService commonCodeGroupService;

    private CommonCodeGroup testGroup;
    private CommonCodeGroupDto testGroupDto;
    private CommonCodeGroupCreateRequest testCreateRequest;

    @BeforeEach
    void setUp() {
        testGroup = CommonCodeGroup.builder()
                .groupCode("TEST_GRP")
                .groupName("Test Group")
                .description("Test Description")
                .useYn("Y")
                .sortOrder(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testGroupDto = CommonCodeGroupDto.builder()
                .groupCode("TEST_GRP")
                .groupName("Test Group")
                .description("Test Description")
                .useYn("Y")
                .sortOrder(1)
                .build();

        testCreateRequest = new CommonCodeGroupCreateRequest();
        testCreateRequest.setGroupCode("NEW_GRP");
        testCreateRequest.setGroupName("New Group");
        testCreateRequest.setDescription("New Description");
        testCreateRequest.setUseYn("Y");
        testCreateRequest.setSortOrder(1);
    }

    @Test
    void getAllGroups_shouldReturnAllGroups() {
        CommonCodeGroup group2 = CommonCodeGroup.builder()
                .groupCode("TEST_GRP2")
                .groupName("Test Group 2")
                .useYn("Y")
                .sortOrder(2)
                .build();

        when(commonCodeGroupRepository.findAll()).thenReturn(Arrays.asList(testGroup, group2));

        List<CommonCodeGroupDto> result = commonCodeGroupService.getAllGroups();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getGroupCode()).isEqualTo("TEST_GRP");
        assertThat(result.get(1).getGroupCode()).isEqualTo("TEST_GRP2");
        verify(commonCodeGroupRepository).findAll();
    }

    @Test
    void getAllGroupsWithPageable_shouldReturnPagedGroups() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CommonCodeGroup> groupPage = new PageImpl<>(Arrays.asList(testGroup));

        when(commonCodeGroupRepository.findAll(pageable)).thenReturn(groupPage);

        Page<CommonCodeGroupDto> result = commonCodeGroupService.getAllGroups(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getGroupCode()).isEqualTo("TEST_GRP");
        verify(commonCodeGroupRepository).findAll(pageable);
    }

    @Test
    void getActiveGroups_shouldReturnOnlyActiveGroups() {
        when(commonCodeGroupRepository.findByUseYnOrderBySortOrderAsc("Y")).thenReturn(Arrays.asList(testGroup));

        List<CommonCodeGroupDto> result = commonCodeGroupService.getActiveGroups();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUseYn()).isEqualTo("Y");
        verify(commonCodeGroupRepository).findByUseYnOrderBySortOrderAsc("Y");
    }

    @Test
    void getGroupByGroupCode_shouldReturnGroup_whenGroupExists() {
        when(commonCodeGroupRepository.findById("TEST_GRP")).thenReturn(Optional.of(testGroup));

        CommonCodeGroupDto result = commonCodeGroupService.getGroupByGroupCode("TEST_GRP");

        assertThat(result.getGroupCode()).isEqualTo("TEST_GRP");
        assertThat(result.getGroupName()).isEqualTo("Test Group");
        verify(commonCodeGroupRepository).findById("TEST_GRP");
    }

    @Test
    void getGroupByGroupCode_shouldThrowException_whenGroupNotFound() {
        when(commonCodeGroupRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonCodeGroupService.getGroupByGroupCode("NONEXISTENT"))
                .isInstanceOf(CommonCodeGroupNotFoundException.class);
        verify(commonCodeGroupRepository).findById("NONEXISTENT");
    }

    @Test
    void searchGroups_shouldReturnMatchingGroups() {
        when(commonCodeGroupRepository.searchByKeyword("test")).thenReturn(Arrays.asList(testGroup));

        List<CommonCodeGroupDto> result = commonCodeGroupService.searchGroups("test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGroupName()).isEqualTo("Test Group");
        verify(commonCodeGroupRepository).searchByKeyword("test");
    }

    @Test
    void createGroup_shouldCreateNewGroup_whenValidRequest() {
        when(commonCodeGroupRepository.existsByGroupCode("NEW_GRP")).thenReturn(false);
        when(commonCodeGroupRepository.save(any(CommonCodeGroup.class))).thenReturn(testGroup);

        CommonCodeGroupDto result = commonCodeGroupService.createGroup(testCreateRequest);

        assertThat(result).isNotNull();
        verify(commonCodeGroupRepository).existsByGroupCode("NEW_GRP");
        verify(commonCodeGroupRepository).save(any(CommonCodeGroup.class));
    }

    @Test
    void createGroup_shouldThrowException_whenGroupCodeExists() {
        when(commonCodeGroupRepository.existsByGroupCode("NEW_GRP")).thenReturn(true);

        assertThatThrownBy(() -> commonCodeGroupService.createGroup(testCreateRequest))
                .isInstanceOf(CommonCodeAlreadyExistsException.class);
        verify(commonCodeGroupRepository).existsByGroupCode("NEW_GRP");
        verify(commonCodeGroupRepository, never()).save(any(CommonCodeGroup.class));
    }

    @Test
    void createGroup_shouldSetDefaultUseYn_whenUseYnNotProvided() {
        testCreateRequest.setUseYn(null);
        when(commonCodeGroupRepository.existsByGroupCode("NEW_GRP")).thenReturn(false);
        when(commonCodeGroupRepository.save(any(CommonCodeGroup.class))).thenReturn(testGroup);

        CommonCodeGroupDto result = commonCodeGroupService.createGroup(testCreateRequest);

        assertThat(result).isNotNull();
        verify(commonCodeGroupRepository).save(argThat(group -> "Y".equals(group.getUseYn())));
    }

    @Test
    void updateGroup_shouldUpdateGroup_whenValidRequest() {
        when(commonCodeGroupRepository.findById("TEST_GRP")).thenReturn(Optional.of(testGroup));
        when(commonCodeGroupRepository.save(any(CommonCodeGroup.class))).thenReturn(testGroup);

        CommonCodeGroupCreateRequest updateRequest = new CommonCodeGroupCreateRequest();
        updateRequest.setGroupName("Updated Group");
        updateRequest.setDescription("Updated Description");

        CommonCodeGroupDto result = commonCodeGroupService.updateGroup("TEST_GRP", updateRequest);

        assertThat(result).isNotNull();
        verify(commonCodeGroupRepository).findById("TEST_GRP");
        verify(commonCodeGroupRepository).save(any(CommonCodeGroup.class));
    }

    @Test
    void updateGroup_shouldThrowException_whenGroupNotFound() {
        when(commonCodeGroupRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        CommonCodeGroupCreateRequest updateRequest = new CommonCodeGroupCreateRequest();
        updateRequest.setGroupName("Updated Group");

        assertThatThrownBy(() -> commonCodeGroupService.updateGroup("NONEXISTENT", updateRequest))
                .isInstanceOf(CommonCodeGroupNotFoundException.class);
        verify(commonCodeGroupRepository).findById("NONEXISTENT");
        verify(commonCodeGroupRepository, never()).save(any(CommonCodeGroup.class));
    }

    @Test
    void deleteGroup_shouldDeleteGroup_whenGroupExists() {
        when(commonCodeGroupRepository.existsById("TEST_GRP")).thenReturn(true);

        commonCodeGroupService.deleteGroup("TEST_GRP");

        verify(commonCodeGroupRepository).existsById("TEST_GRP");
        verify(commonCodeGroupRepository).deleteById("TEST_GRP");
    }

    @Test
    void deleteGroup_shouldThrowException_whenGroupNotFound() {
        when(commonCodeGroupRepository.existsById("NONEXISTENT")).thenReturn(false);

        assertThatThrownBy(() -> commonCodeGroupService.deleteGroup("NONEXISTENT"))
                .isInstanceOf(CommonCodeGroupNotFoundException.class);
        verify(commonCodeGroupRepository).existsById("NONEXISTENT");
        verify(commonCodeGroupRepository, never()).deleteById(anyString());
    }
}