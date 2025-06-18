package net.devgrr.springbootinit.service;

import net.devgrr.springbootinit.dto.CategoryCreateRequest;
import net.devgrr.springbootinit.dto.CategoryDto;
import net.devgrr.springbootinit.entity.Category;
import net.devgrr.springbootinit.exception.CategoryNotFoundException;
import net.devgrr.springbootinit.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory;
    private Category parentCategory;
    private CategoryCreateRequest testCreateRequest;

    @BeforeEach
    void setUp() {
        parentCategory = Category.builder()
                .id(1L)
                .name("Electronics")
                .description("Electronic products")
                .displayOrder(1)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testCategory = Category.builder()
                .id(2L)
                .name("Smartphones")
                .description("Mobile phones")
                .displayOrder(1)
                .isActive(true)
                .parent(parentCategory)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testCreateRequest = new CategoryCreateRequest();
        testCreateRequest.setName("New Category");
        testCreateRequest.setDescription("New Description");
        testCreateRequest.setDisplayOrder(2);
        testCreateRequest.setIsActive(true);
        testCreateRequest.setParentId(1L);
    }

    @Test
    void getAllCategories_shouldReturnAllActiveCategories() {
        when(categoryRepository.findByIsActiveTrueOrderByDisplayOrderAsc())
                .thenReturn(Arrays.asList(testCategory));

        List<CategoryDto> result = categoryService.getAllCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Smartphones");
        verify(categoryRepository).findByIsActiveTrueOrderByDisplayOrderAsc();
    }

    @Test
    void getAllCategoriesWithPageable_shouldReturnPagedCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(Arrays.asList(testCategory));
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        Page<CategoryDto> result = categoryService.getAllCategories(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Smartphones");
        verify(categoryRepository).findAll(pageable);
    }

    @Test
    void getRootCategories_shouldReturnOnlyRootCategories() {
        when(categoryRepository.findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc())
                .thenReturn(Arrays.asList(parentCategory));

        List<CategoryDto> result = categoryService.getRootCategories();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Electronics");
        assertThat(result.get(0).getParentId()).isNull();
        verify(categoryRepository).findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc();
    }

    @Test
    void getSubCategories_shouldReturnSubCategoriesOfParent() {
        when(categoryRepository.findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(1L))
                .thenReturn(Arrays.asList(testCategory));

        List<CategoryDto> result = categoryService.getSubCategories(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Smartphones");
        assertThat(result.get(0).getParentId()).isEqualTo(1L);
        verify(categoryRepository).findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(1L);
    }

    @Test
    void getCategoryById_shouldReturnCategory_whenCategoryExists() {
        when(categoryRepository.findByIdAndIsActiveTrue(2L)).thenReturn(Optional.of(testCategory));

        CategoryDto result = categoryService.getCategoryById(2L);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Smartphones");
        verify(categoryRepository).findByIdAndIsActiveTrue(2L);
    }

    @Test
    void getCategoryById_shouldThrowException_whenCategoryNotFound() {
        when(categoryRepository.findByIdAndIsActiveTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryById(99L))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository).findByIdAndIsActiveTrue(99L);
    }

    @Test
    void searchCategories_shouldReturnMatchingCategories() {
        when(categoryRepository.searchByKeyword("Smart"))
                .thenReturn(Arrays.asList(testCategory));

        List<CategoryDto> result = categoryService.searchCategories("Smart");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Smartphones");
        verify(categoryRepository).searchByKeyword("Smart");
    }

    @Test
    void createCategory_shouldCreateNewCategory_whenValidRequest() {
        when(categoryRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.existsByNameAndParentId("New Category", 1L)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        CategoryDto result = categoryService.createCategory(testCreateRequest);

        assertThat(result).isNotNull();
        verify(categoryRepository).findByIdAndIsActiveTrue(1L);
        verify(categoryRepository).existsByNameAndParentId("New Category", 1L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategory_shouldCreateRootCategory_whenNoParentId() {
        testCreateRequest.setParentId(null);
        when(categoryRepository.existsByNameAndParentId("New Category", null)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(parentCategory);

        CategoryDto result = categoryService.createCategory(testCreateRequest);

        assertThat(result).isNotNull();
        verify(categoryRepository).existsByNameAndParentId("New Category", null);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategory_shouldThrowException_whenParentNotFound() {
        when(categoryRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.createCategory(testCreateRequest))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository).findByIdAndIsActiveTrue(1L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void createCategory_shouldThrowException_whenCategoryNameAlreadyExists() {
        when(categoryRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.existsByNameAndParentId("New Category", 1L)).thenReturn(true);

        assertThatThrownBy(() -> categoryService.createCategory(testCreateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldUpdateCategory_whenValidRequest() {
        CategoryCreateRequest updateRequest = new CategoryCreateRequest();
        updateRequest.setName("Updated Category");
        updateRequest.setDescription("Updated Description");

        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByNameAndParentIdAndIdNot("Updated Category", null, 2L)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        CategoryDto result = categoryService.updateCategory(2L, updateRequest);

        assertThat(result).isNotNull();
        verify(categoryRepository).findById(2L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldThrowException_whenCategoryNotFound() {
        CategoryCreateRequest updateRequest = new CategoryCreateRequest();
        updateRequest.setName("Updated Category");

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(99L, updateRequest))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository).findById(99L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_shouldThrowException_whenNameAlreadyExists() {
        CategoryCreateRequest updateRequest = new CategoryCreateRequest();
        updateRequest.setName("Existing Category");

        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.existsByNameAndParentIdAndIdNot("Existing Category", null, 2L)).thenReturn(true);

        assertThatThrownBy(() -> categoryService.updateCategory(2L, updateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteCategory_shouldSetIsActiveToFalse() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        categoryService.deleteCategory(2L);

        verify(categoryRepository).findById(2L);
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void deleteCategory_shouldThrowException_whenCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategory(99L))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository).findById(99L);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void convertToDto_shouldMapAllFields() {
        when(categoryRepository.findByIsActiveTrueOrderByDisplayOrderAsc())
                .thenReturn(Arrays.asList(testCategory));

        List<CategoryDto> categories = categoryService.getAllCategories();
        CategoryDto dto = categories.get(0);

        assertThat(dto.getId()).isEqualTo(testCategory.getId());
        assertThat(dto.getName()).isEqualTo(testCategory.getName());
        assertThat(dto.getDescription()).isEqualTo(testCategory.getDescription());
        assertThat(dto.getDisplayOrder()).isEqualTo(testCategory.getDisplayOrder());
        assertThat(dto.getIsActive()).isEqualTo(testCategory.getIsActive());
        assertThat(dto.getParentId()).isEqualTo(testCategory.getParent().getId());
        assertThat(dto.getParentName()).isEqualTo(testCategory.getParent().getName());
    }
}