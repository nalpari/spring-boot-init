package net.devgrr.springbootinit.service;

import net.devgrr.springbootinit.dto.ProductCreateRequest;
import net.devgrr.springbootinit.dto.ProductDto;
import net.devgrr.springbootinit.dto.ProductUpdateRequest;
import net.devgrr.springbootinit.dto.StockUpdateRequest;
import net.devgrr.springbootinit.entity.Category;
import net.devgrr.springbootinit.entity.Product;
import net.devgrr.springbootinit.entity.ProductStatus;
import net.devgrr.springbootinit.entity.User;
import net.devgrr.springbootinit.exception.CategoryNotFoundException;
import net.devgrr.springbootinit.exception.ProductAlreadyExistsException;
import net.devgrr.springbootinit.exception.ProductNotFoundException;
import net.devgrr.springbootinit.repository.CategoryRepository;
import net.devgrr.springbootinit.repository.ProductRepository;
import net.devgrr.springbootinit.repository.UserRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProductService productService;

    private Category testCategory;
    private Product testProduct;
    private User testUser;
    private ProductCreateRequest testCreateRequest;
    private ProductUpdateRequest testUpdateRequest;

    @BeforeEach
    void setUp() {
        testCategory = Category.builder()
                .id(1L)
                .name("Electronics")
                .isActive(true)
                .build();

        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .build();

        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .sku("TEST-001")
                .price(new BigDecimal("99.99"))
                .stockQuantity(100)
                .minStockLevel(10)
                .status(ProductStatus.ACTIVE)
                .category(testCategory)
                .createdBy(testUser)
                .updatedBy(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testCreateRequest = new ProductCreateRequest();
        testCreateRequest.setName("New Product");
        testCreateRequest.setDescription("New Description");
        testCreateRequest.setSku("NEW-001");
        testCreateRequest.setPrice(new BigDecimal("199.99"));
        testCreateRequest.setStockQuantity(50);
        testCreateRequest.setCategoryId(1L);

        testUpdateRequest = new ProductUpdateRequest();
        testUpdateRequest.setName("Updated Product");
        testUpdateRequest.setPrice(new BigDecimal("299.99"));

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        List<ProductDto> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Product");
        verify(productRepository).findAll();
    }

    @Test
    void getAllProductsWithPageable_shouldReturnPagedProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductDto> result = productService.getAllProducts(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Test Product");
        verify(productRepository).findAll(pageable);
    }

    @Test
    void getActiveProducts_shouldReturnOnlyActiveProducts() {
        when(productRepository.findByStatusOrderByNameAsc(ProductStatus.ACTIVE))
                .thenReturn(Arrays.asList(testProduct));

        List<ProductDto> result = productService.getActiveProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(ProductStatus.ACTIVE);
        verify(productRepository).findByStatusOrderByNameAsc(ProductStatus.ACTIVE);
    }

    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        ProductDto result = productService.getProductById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Product");
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductBySku_shouldReturnProduct_whenSkuExists() {
        when(productRepository.findBySku("TEST-001")).thenReturn(Optional.of(testProduct));

        ProductDto result = productService.getProductBySku("TEST-001");

        assertThat(result.getSku()).isEqualTo("TEST-001");
        verify(productRepository).findBySku("TEST-001");
    }

    @Test
    void getProductBySku_shouldThrowException_whenSkuNotFound() {
        when(productRepository.findBySku("NONEXISTENT")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductBySku("NONEXISTENT"))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository).findBySku("NONEXISTENT");
    }

    @Test
    void createProduct_shouldCreateNewProduct_whenValidRequest() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.existsBySku("NEW-001")).thenReturn(false);
        when(categoryRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.of(testCategory));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductDto result = productService.createProduct(testCreateRequest);

        assertThat(result).isNotNull();
        verify(productRepository).existsBySku("NEW-001");
        verify(categoryRepository).findByIdAndIsActiveTrue(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_shouldThrowException_whenSkuAlreadyExists() {
        when(productRepository.existsBySku("NEW-001")).thenReturn(true);

        assertThatThrownBy(() -> productService.createProduct(testCreateRequest))
                .isInstanceOf(ProductAlreadyExistsException.class);
        verify(productRepository).existsBySku("NEW-001");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createProduct_shouldThrowException_whenCategoryNotFound() {
        when(productRepository.existsBySku("NEW-001")).thenReturn(false);
        when(categoryRepository.findByIdAndIsActiveTrue(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(testCreateRequest))
                .isInstanceOf(CategoryNotFoundException.class);
        verify(categoryRepository).findByIdAndIsActiveTrue(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldUpdateProduct_whenValidRequest() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductDto result = productService.updateProduct(1L, testUpdateRequest);

        assertThat(result).isNotNull();
        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(1L, testUpdateRequest))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateStock_shouldUpdateStockQuantity() {
        StockUpdateRequest stockRequest = new StockUpdateRequest();
        stockRequest.setQuantity(50);
        stockRequest.setReason("Stock adjustment");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductDto result = productService.updateStock(1L, stockRequest);

        assertThat(result).isNotNull();
        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_shouldSetStatusToDiscontinued() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(1L))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void getLowStockProducts_shouldReturnLowStockProducts() {
        when(productRepository.findLowStockProducts()).thenReturn(Arrays.asList(testProduct));

        List<ProductDto> result = productService.getLowStockProducts();

        assertThat(result).hasSize(1);
        verify(productRepository).findLowStockProducts();
    }

    @Test
    void getOutOfStockProducts_shouldReturnOutOfStockProducts() {
        when(productRepository.findOutOfStockProducts()).thenReturn(Arrays.asList(testProduct));

        List<ProductDto> result = productService.getOutOfStockProducts();

        assertThat(result).hasSize(1);
        verify(productRepository).findOutOfStockProducts();
    }

    @Test
    void getProductsByCategory_shouldReturnProductsInCategory() {
        when(productRepository.findByCategoryIdAndStatusOrderByNameAsc(1L, ProductStatus.ACTIVE))
                .thenReturn(Arrays.asList(testProduct));

        List<ProductDto> result = productService.getProductsByCategory(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategoryId()).isEqualTo(1L);
        verify(productRepository).findByCategoryIdAndStatusOrderByNameAsc(1L, ProductStatus.ACTIVE);
    }

    @Test
    void searchProducts_shouldReturnMatchingProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));
        when(productRepository.searchProducts("Test", ProductStatus.ACTIVE, 1L, pageable))
                .thenReturn(productPage);

        Page<ProductDto> result = productService.searchProducts("Test", ProductStatus.ACTIVE, 1L, pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(productRepository).searchProducts("Test", ProductStatus.ACTIVE, 1L, pageable);
    }

    @Test
    void getProductsByPriceRange_shouldReturnProductsInPriceRange() {
        BigDecimal minPrice = new BigDecimal("50.00");
        BigDecimal maxPrice = new BigDecimal("150.00");
        when(productRepository.findByPriceRange(minPrice, maxPrice))
                .thenReturn(Arrays.asList(testProduct));

        List<ProductDto> result = productService.getProductsByPriceRange(minPrice, maxPrice);

        assertThat(result).hasSize(1);
        verify(productRepository).findByPriceRange(minPrice, maxPrice);
    }

    @Test
    void updateProduct_shouldUpdateCategory_whenCategoryIdProvided() {
        Category newCategory = Category.builder()
                .id(2L)
                .name("New Category")
                .isActive(true)
                .build();

        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        updateRequest.setCategoryId(2L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findByIdAndIsActiveTrue(2L)).thenReturn(Optional.of(newCategory));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        ProductDto result = productService.updateProduct(1L, updateRequest);

        assertThat(result).isNotNull();
        verify(categoryRepository).findByIdAndIsActiveTrue(2L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldThrowException_whenSkuAlreadyExists() {
        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        updateRequest.setSku("EXISTING-SKU");

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.existsBySkuAndIdNot("EXISTING-SKU", 1L)).thenReturn(true);

        assertThatThrownBy(() -> productService.updateProduct(1L, updateRequest))
                .isInstanceOf(ProductAlreadyExistsException.class);
        verify(productRepository).existsBySkuAndIdNot("EXISTING-SKU", 1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateStock_shouldChangeStatusToOutOfStock_whenQuantityIsZero() {
        StockUpdateRequest stockRequest = new StockUpdateRequest();
        stockRequest.setQuantity(0);
        stockRequest.setReason("Sold out");

        Product outOfStockProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .stockQuantity(0)
                .minStockLevel(10)
                .status(ProductStatus.ACTIVE)
                .category(testCategory)
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(outOfStockProduct));
        when(productRepository.save(any(Product.class))).thenReturn(outOfStockProduct);

        ProductDto result = productService.updateStock(1L, stockRequest);

        assertThat(result).isNotNull();
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateStock_shouldChangeStatusToActive_whenStockIsRestored() {
        StockUpdateRequest stockRequest = new StockUpdateRequest();
        stockRequest.setQuantity(50);
        stockRequest.setReason("Stock replenished");

        Product outOfStockProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .stockQuantity(0)
                .minStockLevel(10)
                .status(ProductStatus.OUT_OF_STOCK)
                .category(testCategory)
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(outOfStockProduct));
        when(productRepository.save(any(Product.class))).thenReturn(outOfStockProduct);

        ProductDto result = productService.updateStock(1L, stockRequest);

        assertThat(result).isNotNull();
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateStock_shouldThrowException_whenProductNotFound() {
        StockUpdateRequest stockRequest = new StockUpdateRequest();
        stockRequest.setQuantity(50);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateStock(1L, stockRequest))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void convertToDto_shouldMapAllFields() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(testProduct));

        List<ProductDto> products = productService.getAllProducts();
        ProductDto dto = products.get(0);

        assertThat(dto.getId()).isEqualTo(testProduct.getId());
        assertThat(dto.getName()).isEqualTo(testProduct.getName());
        assertThat(dto.getSku()).isEqualTo(testProduct.getSku());
        assertThat(dto.getPrice()).isEqualTo(testProduct.getPrice());
        assertThat(dto.getStockQuantity()).isEqualTo(testProduct.getStockQuantity());
        assertThat(dto.getStatus()).isEqualTo(testProduct.getStatus());
    }
}