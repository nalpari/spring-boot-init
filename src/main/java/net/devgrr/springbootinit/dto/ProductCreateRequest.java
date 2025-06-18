package net.devgrr.springbootinit.dto;

import lombok.Data;
import net.devgrr.springbootinit.entity.ProductStatus;

import java.math.BigDecimal;

@Data
public class ProductCreateRequest {
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
}