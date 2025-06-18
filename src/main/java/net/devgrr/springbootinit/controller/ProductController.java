package net.devgrr.springbootinit.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.devgrr.springbootinit.dto.ProductCreateRequest;
import net.devgrr.springbootinit.dto.ProductDto;
import net.devgrr.springbootinit.dto.ProductUpdateRequest;
import net.devgrr.springbootinit.dto.StockUpdateRequest;
import net.devgrr.springbootinit.entity.ProductStatus;
import net.devgrr.springbootinit.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Product CRUD operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/page")
    @Operation(summary = "Get products with pagination", description = "Retrieve products with pagination")
    public ResponseEntity<Page<ProductDto>> getAllProducts(Pageable pageable) {
        Page<ProductDto> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active products", description = "Retrieve only active products")
    public ResponseEntity<List<ProductDto>> getActiveProducts() {
        List<ProductDto> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category", description = "Retrieve products by category ID")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Get product by SKU", description = "Retrieve a specific product by SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable String sku) {
        ProductDto product = productService.getProductBySku(sku);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by keyword with filters")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @Parameter(description = "Search keyword") @RequestParam String keyword,
            @Parameter(description = "Product status filter") @RequestParam(required = false) ProductStatus status,
            @Parameter(description = "Category filter") @RequestParam(required = false) Long categoryId,
            Pageable pageable) {
        Page<ProductDto> products = productService.searchProducts(keyword, status, categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock products", description = "Retrieve products with low stock levels")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> getLowStockProducts() {
        List<ProductDto> products = productService.getLowStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out of stock products", description = "Retrieve products that are out of stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDto>> getOutOfStockProducts() {
        List<ProductDto> products = productService.getOutOfStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range", description = "Retrieve products within a price range")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        List<ProductDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or product already exists")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreateRequest request) {
        ProductDto product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update product information (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id, 
            @RequestBody ProductUpdateRequest request) {
        ProductDto product = productService.updateProduct(id, request);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Update product stock quantity (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateStock(
            @PathVariable Long id, 
            @RequestBody StockUpdateRequest request) {
        ProductDto product = productService.updateStock(id, request);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a product (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}