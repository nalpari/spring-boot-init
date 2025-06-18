package net.devgrr.springbootinit.service;

import lombok.RequiredArgsConstructor;
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
import net.devgrr.springbootinit.exception.UserNotFoundException;
import net.devgrr.springbootinit.repository.CategoryRepository;
import net.devgrr.springbootinit.repository.ProductRepository;
import net.devgrr.springbootinit.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getActiveProducts() {
        return productRepository.findByStatusOrderByNameAsc(ProductStatus.ACTIVE)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryIdAndStatusOrderByNameAsc(categoryId, ProductStatus.ACTIVE)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return convertToDto(product);
    }

    @Transactional(readOnly = true)
    public ProductDto getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku));
        return convertToDto(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> searchProducts(String keyword, ProductStatus status, Long categoryId, Pageable pageable) {
        return productRepository.searchProducts(keyword, status, categoryId, pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getLowStockProducts() {
        return productRepository.findLowStockProducts()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(ProductCreateRequest request) {
        if (request.getSku() != null && productRepository.existsBySku(request.getSku())) {
            throw new ProductAlreadyExistsException(request.getSku());
        }

        Category category = categoryRepository.findByIdAndIsActiveTrue(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));

        User currentUser = getCurrentUser();

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .sku(request.getSku())
                .price(request.getPrice())
                .costPrice(request.getCostPrice())
                .stockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0)
                .minStockLevel(request.getMinStockLevel())
                .weight(request.getWeight())
                .dimensions(request.getDimensions())
                .imageUrl(request.getImageUrl())
                .status(request.getStatus() != null ? request.getStatus() : ProductStatus.ACTIVE)
                .category(category)
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (request.getSku() != null && !request.getSku().equals(product.getSku())) {
            if (productRepository.existsBySkuAndIdNot(request.getSku(), id)) {
                throw new ProductAlreadyExistsException(request.getSku());
            }
            product.setSku(request.getSku());
        }

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getCostPrice() != null) {
            product.setCostPrice(request.getCostPrice());
        }

        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }

        if (request.getMinStockLevel() != null) {
            product.setMinStockLevel(request.getMinStockLevel());
        }

        if (request.getWeight() != null) {
            product.setWeight(request.getWeight());
        }

        if (request.getDimensions() != null) {
            product.setDimensions(request.getDimensions());
        }

        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }

        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }

        if (request.getCategoryId() != null && !request.getCategoryId().equals(product.getCategory().getId())) {
            Category category = categoryRepository.findByIdAndIsActiveTrue(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getCategoryId()));
            product.setCategory(category);
        }

        product.setUpdatedBy(getCurrentUser());

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    public ProductDto updateStock(Long id, StockUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setStockQuantity(request.getQuantity());
        product.setUpdatedBy(getCurrentUser());

        if (product.isOutOfStock()) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        } else if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setStatus(ProductStatus.DISCONTINUED);
        product.setUpdatedBy(getCurrentUser());
        productRepository.save(product);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("username", username));
    }

    private ProductDto convertToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .sku(product.getSku())
                .price(product.getPrice())
                .costPrice(product.getCostPrice())
                .stockQuantity(product.getStockQuantity())
                .minStockLevel(product.getMinStockLevel())
                .weight(product.getWeight())
                .dimensions(product.getDimensions())
                .imageUrl(product.getImageUrl())
                .status(product.getStatus())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .createdById(product.getCreatedBy() != null ? product.getCreatedBy().getId() : null)
                .createdByUsername(product.getCreatedBy() != null ? product.getCreatedBy().getUsername() : null)
                .updatedById(product.getUpdatedBy() != null ? product.getUpdatedBy().getId() : null)
                .updatedByUsername(product.getUpdatedBy() != null ? product.getUpdatedBy().getUsername() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .isLowStock(product.isLowStock())
                .isOutOfStock(product.isOutOfStock())
                .build();
    }
}