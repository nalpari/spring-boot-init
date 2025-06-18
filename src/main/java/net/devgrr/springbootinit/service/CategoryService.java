package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.CategoryCreateRequest;
import net.devgrr.springbootinit.dto.CategoryDto;
import net.devgrr.springbootinit.entity.Category;
import net.devgrr.springbootinit.exception.CategoryNotFoundException;
import net.devgrr.springbootinit.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getRootCategories() {
        return categoryRepository.findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getSubCategories(Long parentId) {
        return categoryRepository.findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(parentId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return convertToDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> searchCategories(String keyword) {
        return categoryRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto createCategory(CategoryCreateRequest request) {
        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findByIdAndIsActiveTrue(request.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getParentId()));
        }

        if (categoryRepository.existsByNameAndParentId(request.getName(), request.getParentId())) {
            throw new RuntimeException("Category with name '" + request.getName() + "' already exists in this parent category");
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .parent(parent)
                .build();

        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    public CategoryDto updateCategory(Long id, CategoryCreateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (request.getName() != null) {
            if (categoryRepository.existsByNameAndParentIdAndIdNot(request.getName(), request.getParentId(), id)) {
                throw new RuntimeException("Category with name '" + request.getName() + "' already exists in this parent category");
            }
            category.setName(request.getName());
        }

        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        if (request.getDisplayOrder() != null) {
            category.setDisplayOrder(request.getDisplayOrder());
        }

        if (request.getIsActive() != null) {
            category.setIsActive(request.getIsActive());
        }

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findByIdAndIsActiveTrue(request.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getParentId()));
            category.setParent(parent);
        }

        Category updatedCategory = categoryRepository.save(category);
        return convertToDto(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setIsActive(false);
        categoryRepository.save(category);
    }

    private CategoryDto convertToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .displayOrder(category.getDisplayOrder())
                .isActive(category.getIsActive())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .parentName(category.getParent() != null ? category.getParent().getName() : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}