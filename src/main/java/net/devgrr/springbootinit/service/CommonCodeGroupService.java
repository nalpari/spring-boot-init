package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.CommonCodeGroupCreateRequest;
import net.devgrr.springbootinit.dto.CommonCodeGroupDto;
import net.devgrr.springbootinit.entity.CommonCodeGroup;
import net.devgrr.springbootinit.exception.CommonCodeAlreadyExistsException;
import net.devgrr.springbootinit.exception.CommonCodeGroupNotFoundException;
import net.devgrr.springbootinit.repository.CommonCodeGroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonCodeGroupService {

    private final CommonCodeGroupRepository commonCodeGroupRepository;

    @Transactional(readOnly = true)
    public List<CommonCodeGroupDto> getAllGroups() {
        return commonCodeGroupRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CommonCodeGroupDto> getAllGroups(Pageable pageable) {
        return commonCodeGroupRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeGroupDto> getActiveGroups() {
        return commonCodeGroupRepository.findByUseYnOrderBySortOrderAsc("Y")
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommonCodeGroupDto getGroupByGroupCode(String groupCode) {
        CommonCodeGroup group = commonCodeGroupRepository.findById(groupCode)
                .orElseThrow(() -> new CommonCodeGroupNotFoundException(groupCode));
        return convertToDto(group);
    }

    @Transactional(readOnly = true)
    public List<CommonCodeGroupDto> searchGroups(String keyword) {
        return commonCodeGroupRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CommonCodeGroupDto createGroup(CommonCodeGroupCreateRequest request) {
        if (commonCodeGroupRepository.existsByGroupCode(request.getGroupCode())) {
            throw new CommonCodeAlreadyExistsException(request.getGroupCode());
        }

        CommonCodeGroup group = CommonCodeGroup.builder()
                .groupCode(request.getGroupCode())
                .groupName(request.getGroupName())
                .description(request.getDescription())
                .useYn(request.getUseYn() != null ? request.getUseYn() : "Y")
                .sortOrder(request.getSortOrder())
                .build();

        CommonCodeGroup savedGroup = commonCodeGroupRepository.save(group);
        return convertToDto(savedGroup);
    }

    public CommonCodeGroupDto updateGroup(String groupCode, CommonCodeGroupCreateRequest request) {
        CommonCodeGroup group = commonCodeGroupRepository.findById(groupCode)
                .orElseThrow(() -> new CommonCodeGroupNotFoundException(groupCode));

        if (request.getGroupName() != null) {
            group.setGroupName(request.getGroupName());
        }
        if (request.getDescription() != null) {
            group.setDescription(request.getDescription());
        }
        if (request.getUseYn() != null) {
            group.setUseYn(request.getUseYn());
        }
        if (request.getSortOrder() != null) {
            group.setSortOrder(request.getSortOrder());
        }

        CommonCodeGroup updatedGroup = commonCodeGroupRepository.save(group);
        return convertToDto(updatedGroup);
    }

    public void deleteGroup(String groupCode) {
        if (!commonCodeGroupRepository.existsById(groupCode)) {
            throw new CommonCodeGroupNotFoundException(groupCode);
        }
        commonCodeGroupRepository.deleteById(groupCode);
    }

    private CommonCodeGroupDto convertToDto(CommonCodeGroup group) {
        return CommonCodeGroupDto.builder()
                .groupCode(group.getGroupCode())
                .groupName(group.getGroupName())
                .description(group.getDescription())
                .useYn(group.getUseYn())
                .sortOrder(group.getSortOrder())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
    }
}