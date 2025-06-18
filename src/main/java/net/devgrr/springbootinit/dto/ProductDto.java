package net.devgrr.springbootinit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.devgrr.springbootinit.entity.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;
    private BigDecimal costPrice;
    private Integer stockQuantity;
    private Integer minStockLevel;
    private BigDecimal weight;
    private String dimensions;
    private String imageUrl;
    private ProductStatus status;
    private Long categoryId;
    private String categoryName;
    private Long createdById;
    private String createdByUsername;
    private Long updatedById;
    private String updatedByUsername;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isLowStock;
    private Boolean isOutOfStock;
}