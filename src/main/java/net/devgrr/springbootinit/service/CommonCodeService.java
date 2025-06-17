package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;
    private final CommonCodeGroupRepository commonCodeGroupRepository;

    @Transactional(readOnly = true)
    public List<CommonCodeDto> getAllCodes() {
        return commonCodeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CommonCodeDto> getAllCodes(Pageable pageable) {
        return commonCodeRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeDto> getCodesByGroupCode(String groupCode) {
        return commonCodeRepository.findByCodeGroup_GroupCodeAndUseYnOrderBySortOrderAsc(groupCode, "Y")
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommonCodeDto getCodeById(Long id) {
        CommonCode code = commonCodeRepository.findById(id)
                .orElseThrow(() -> new CommonCodeNotFoundException(id));
        return convertToDto(code);
    }

    @Transactional(readOnly = true)
    public CommonCodeDto getCodeByGroupCodeAndCode(String groupCode, String code) {
        CommonCode commonCode = commonCodeRepository.findByCodeGroup_GroupCodeAndCode(groupCode, code)
                .orElseThrow(() -> new CommonCodeNotFoundException(groupCode, code));
        return convertToDto(commonCode);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeDto> searchCodes(String keyword) {
        return commonCodeRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommonCodeDto> searchCodesByGroupCode(String groupCode, String keyword) {
        return commonCodeRepository.searchByGroupCodeAndKeyword(groupCode, keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommonCodeDto createCode(CommonCodeCreateRequest request) {
        CommonCodeGroup codeGroup = commonCodeGroupRepository.findById(request.getGroupCode())
                .orElseThrow(() -> new CommonCodeGroupNotFoundException(request.getGroupCode()));

        if (commonCodeRepository.existsByCodeGroup_GroupCodeAndCode(request.getGroupCode(), request.getCode())) {
            throw new CommonCodeAlreadyExistsException(request.getGroupCode(), request.getCode());
        }

        CommonCode code = CommonCode.builder()
                .codeGroup(codeGroup)
                .code(request.getCode())
                .codeName(request.getCodeName())
                .codeValue(request.getCodeValue())
                .description(request.getDescription())
                .useYn(request.getUseYn() != null ? request.getUseYn() : "Y")
                .sortOrder(request.getSortOrder())
                .build();

        CommonCode savedCode = commonCodeRepository.save(code);
        return convertToDto(savedCode);
    }

    public CommonCodeDto updateCode(Long id, CommonCodeUpdateRequest request) {
        CommonCode code = commonCodeRepository.findById(id)
                .orElseThrow(() -> new CommonCodeNotFoundException(id));

        if (request.getCodeName() != null) {
            code.setCodeName(request.getCodeName());
        }
        if (request.getCodeValue() != null) {
            code.setCodeValue(request.getCodeValue());
        }
        if (request.getDescription() != null) {
            code.setDescription(request.getDescription());
        }
        if (request.getUseYn() != null) {
            code.setUseYn(request.getUseYn());
        }
        if (request.getSortOrder() != null) {
            code.setSortOrder(request.getSortOrder());
        }

        CommonCode updatedCode = commonCodeRepository.save(code);
        return convertToDto(updatedCode);
    }

    public void deleteCode(Long id) {
        if (!commonCodeRepository.existsById(id)) {
            throw new CommonCodeNotFoundException(id);
        }
        commonCodeRepository.deleteById(id);
    }

    private CommonCodeDto convertToDto(CommonCode code) {
        return CommonCodeDto.builder()
                .id(code.getId())
                .groupCode(code.getCodeGroup().getGroupCode())
                .code(code.getCode())
                .codeName(code.getCodeName())
                .codeValue(code.getCodeValue())
                .description(code.getDescription())
                .useYn(code.getUseYn())
                .sortOrder(code.getSortOrder())
                .createdAt(code.getCreatedAt())
                .updatedAt(code.getUpdatedAt())
                .build();
    }
}