package net.devgrr.springbootinit.service;

import net.devgrr.springbootinit.dto.CommonCodeCreateRequest;
import net.devgrr.springbootinit.dto.CommonCodeDto;
import net.devgrr.springbootinit.dto.CommonCodeUpdateRequest;
import net.devgrr.springbootinit.entity.CommonCode;
import net.devgrr.springbootinit.entity.CommonCodeGroup;
import net.devgrr.springbootinit.exception.CommonCodeAlreadyExistsException;
import net.devgrr.springbootinit.exception.CommonCodeGroupNotFoundException;
import net.devgrr.springbootinit.exception.CommonCodeNotFoundException;
import net.devgrr.springbootinit.repository.CommonCodeGroupRepository;
import net.devgrr.springbootinit.repository.CommonCodeRepository;
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
class CommonCodeServiceTest {

    @Mock
    private CommonCodeRepository commonCodeRepository;

    @Mock
    private CommonCodeGroupRepository commonCodeGroupRepository;

    @InjectMocks
    private CommonCodeService commonCodeService;

    private CommonCodeGroup testGroup;
    private CommonCode testCode;
    private CommonCodeDto testCodeDto;
    private CommonCodeCreateRequest testCreateRequest;
    private CommonCodeUpdateRequest testUpdateRequest;

    @BeforeEach
    void setUp() {
        testGroup = CommonCodeGroup.builder()
                .groupCode("TEST_GRP")
                .groupName("Test Group")
                .useYn("Y")
                .build();

        testCode = CommonCode.builder()
                .id(1L)
                .codeGroup(testGroup)
                .code("TEST_CODE")
                .codeName("Test Code")
                .codeValue("TEST_VALUE")
                .description("Test Description")
                .useYn("Y")
                .sortOrder(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testCodeDto = CommonCodeDto.builder()
                .id(1L)
                .groupCode("TEST_GRP")
                .code("TEST_CODE")
                .codeName("Test Code")
                .codeValue("TEST_VALUE")
                .description("Test Description")
                .useYn("Y")
                .sortOrder(1)
                .build();

        testCreateRequest = new CommonCodeCreateRequest();
        testCreateRequest.setGroupCode("TEST_GRP");
        testCreateRequest.setCode("NEW_CODE");
        testCreateRequest.setCodeName("New Code");
        testCreateRequest.setCodeValue("NEW_VALUE");
        testCreateRequest.setDescription("New Description");
        testCreateRequest.setUseYn("Y");
        testCreateRequest.setSortOrder(1);

        testUpdateRequest = new CommonCodeUpdateRequest();
        testUpdateRequest.setCodeName("Updated Code");
        testUpdateRequest.setCodeValue("UPDATED_VALUE");
        testUpdateRequest.setDescription("Updated Description");
        testUpdateRequest.setUseYn("Y");
        testUpdateRequest.setSortOrder(2);
    }

    @Test
    void getAllCodes_shouldReturnAllCodes() {
        CommonCode code2 = CommonCode.builder()
                .id(2L)
                .codeGroup(testGroup)
                .code("TEST_CODE2")
                .codeName("Test Code 2")
                .useYn("Y")
                .build();

        when(commonCodeRepository.findAll()).thenReturn(Arrays.asList(testCode, code2));

        List<CommonCodeDto> result = commonCodeService.getAllCodes();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCode()).isEqualTo("TEST_CODE");
        assertThat(result.get(1).getCode()).isEqualTo("TEST_CODE2");
        verify(commonCodeRepository).findAll();
    }

    @Test
    void getAllCodesWithPageable_shouldReturnPagedCodes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CommonCode> codePage = new PageImpl<>(Arrays.asList(testCode));

        when(commonCodeRepository.findAll(pageable)).thenReturn(codePage);

        Page<CommonCodeDto> result = commonCodeService.getAllCodes(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCode()).isEqualTo("TEST_CODE");
        verify(commonCodeRepository).findAll(pageable);
    }

    @Test
    void getCodesByGroupCode_shouldReturnCodesForGroup() {
        when(commonCodeRepository.findByCodeGroup_GroupCodeAndUseYnOrderBySortOrderAsc("TEST_GRP", "Y"))
                .thenReturn(Arrays.asList(testCode));

        List<CommonCodeDto> result = commonCodeService.getCodesByGroupCode("TEST_GRP");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGroupCode()).isEqualTo("TEST_GRP");
        verify(commonCodeRepository).findByCodeGroup_GroupCodeAndUseYnOrderBySortOrderAsc("TEST_GRP", "Y");
    }

    @Test
    void getCodeById_shouldReturnCode_whenCodeExists() {
        when(commonCodeRepository.findById(1L)).thenReturn(Optional.of(testCode));

        CommonCodeDto result = commonCodeService.getCodeById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCode()).isEqualTo("TEST_CODE");
        verify(commonCodeRepository).findById(1L);
    }

    @Test
    void getCodeById_shouldThrowException_whenCodeNotFound() {
        when(commonCodeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonCodeService.getCodeById(999L))
                .isInstanceOf(CommonCodeNotFoundException.class);
        verify(commonCodeRepository).findById(999L);
    }

    @Test
    void getCodeByGroupCodeAndCode_shouldReturnCode_whenCodeExists() {
        when(commonCodeRepository.findByCodeGroup_GroupCodeAndCode("TEST_GRP", "TEST_CODE"))
                .thenReturn(Optional.of(testCode));

        CommonCodeDto result = commonCodeService.getCodeByGroupCodeAndCode("TEST_GRP", "TEST_CODE");

        assertThat(result.getGroupCode()).isEqualTo("TEST_GRP");
        assertThat(result.getCode()).isEqualTo("TEST_CODE");
        verify(commonCodeRepository).findByCodeGroup_GroupCodeAndCode("TEST_GRP", "TEST_CODE");
    }

    @Test
    void getCodeByGroupCodeAndCode_shouldThrowException_whenCodeNotFound() {
        when(commonCodeRepository.findByCodeGroup_GroupCodeAndCode("TEST_GRP", "NONEXISTENT"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonCodeService.getCodeByGroupCodeAndCode("TEST_GRP", "NONEXISTENT"))
                .isInstanceOf(CommonCodeNotFoundException.class);
        verify(commonCodeRepository).findByCodeGroup_GroupCodeAndCode("TEST_GRP", "NONEXISTENT");
    }

    @Test
    void searchCodes_shouldReturnMatchingCodes() {
        when(commonCodeRepository.searchByKeyword("test")).thenReturn(Arrays.asList(testCode));

        List<CommonCodeDto> result = commonCodeService.searchCodes("test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCodeName()).isEqualTo("Test Code");
        verify(commonCodeRepository).searchByKeyword("test");
    }

    @Test
    void searchCodesByGroupCode_shouldReturnMatchingCodesForGroup() {
        when(commonCodeRepository.searchByGroupCodeAndKeyword("TEST_GRP", "test"))
                .thenReturn(Arrays.asList(testCode));

        List<CommonCodeDto> result = commonCodeService.searchCodesByGroupCode("TEST_GRP", "test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGroupCode()).isEqualTo("TEST_GRP");
        verify(commonCodeRepository).searchByGroupCodeAndKeyword("TEST_GRP", "test");
    }

    @Test
    void createCode_shouldCreateNewCode_whenValidRequest() {
        when(commonCodeGroupRepository.findById("TEST_GRP")).thenReturn(Optional.of(testGroup));
        when(commonCodeRepository.existsByCodeGroup_GroupCodeAndCode("TEST_GRP", "NEW_CODE")).thenReturn(false);
        when(commonCodeRepository.save(any(CommonCode.class))).thenReturn(testCode);

        CommonCodeDto result = commonCodeService.createCode(testCreateRequest);

        assertThat(result).isNotNull();
        verify(commonCodeGroupRepository).findById("TEST_GRP");
        verify(commonCodeRepository).existsByCodeGroup_GroupCodeAndCode("TEST_GRP", "NEW_CODE");
        verify(commonCodeRepository).save(any(CommonCode.class));
    }

    @Test
    void createCode_shouldThrowException_whenGroupNotFound() {
        testCreateRequest.setGroupCode("NONEXISTENT");
        when(commonCodeGroupRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonCodeService.createCode(testCreateRequest))
                .isInstanceOf(CommonCodeGroupNotFoundException.class);
        verify(commonCodeGroupRepository).findById("NONEXISTENT");
        verify(commonCodeRepository, never()).save(any(CommonCode.class));
    }

    @Test
    void createCode_shouldThrowException_whenCodeExists() {
        when(commonCodeGroupRepository.findById("TEST_GRP")).thenReturn(Optional.of(testGroup));
        when(commonCodeRepository.existsByCodeGroup_GroupCodeAndCode("TEST_GRP", "NEW_CODE")).thenReturn(true);

        assertThatThrownBy(() -> commonCodeService.createCode(testCreateRequest))
                .isInstanceOf(CommonCodeAlreadyExistsException.class);
        verify(commonCodeRepository).existsByCodeGroup_GroupCodeAndCode("TEST_GRP", "NEW_CODE");
        verify(commonCodeRepository, never()).save(any(CommonCode.class));
    }

    @Test
    void createCode_shouldSetDefaultUseYn_whenUseYnNotProvided() {
        testCreateRequest.setUseYn(null);
        when(commonCodeGroupRepository.findById("TEST_GRP")).thenReturn(Optional.of(testGroup));
        when(commonCodeRepository.existsByCodeGroup_GroupCodeAndCode("TEST_GRP", "NEW_CODE")).thenReturn(false);
        when(commonCodeRepository.save(any(CommonCode.class))).thenReturn(testCode);

        CommonCodeDto result = commonCodeService.createCode(testCreateRequest);

        assertThat(result).isNotNull();
        verify(commonCodeRepository).save(argThat(code -> "Y".equals(code.getUseYn())));
    }

    @Test
    void updateCode_shouldUpdateCode_whenValidRequest() {
        when(commonCodeRepository.findById(1L)).thenReturn(Optional.of(testCode));
        when(commonCodeRepository.save(any(CommonCode.class))).thenReturn(testCode);

        CommonCodeDto result = commonCodeService.updateCode(1L, testUpdateRequest);

        assertThat(result).isNotNull();
        verify(commonCodeRepository).findById(1L);
        verify(commonCodeRepository).save(any(CommonCode.class));
    }

    @Test
    void updateCode_shouldThrowException_whenCodeNotFound() {
        when(commonCodeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commonCodeService.updateCode(999L, testUpdateRequest))
                .isInstanceOf(CommonCodeNotFoundException.class);
        verify(commonCodeRepository).findById(999L);
        verify(commonCodeRepository, never()).save(any(CommonCode.class));
    }

    @Test
    void deleteCode_shouldDeleteCode_whenCodeExists() {
        when(commonCodeRepository.existsById(1L)).thenReturn(true);

        commonCodeService.deleteCode(1L);

        verify(commonCodeRepository).existsById(1L);
        verify(commonCodeRepository).deleteById(1L);
    }

    @Test
    void deleteCode_shouldThrowException_whenCodeNotFound() {
        when(commonCodeRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> commonCodeService.deleteCode(999L))
                .isInstanceOf(CommonCodeNotFoundException.class);
        verify(commonCodeRepository).existsById(999L);
        verify(commonCodeRepository, never()).deleteById(anyLong());
    }
}