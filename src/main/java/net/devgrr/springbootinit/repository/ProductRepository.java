package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Product;
import net.devgrr.springbootinit.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByStatusOrderByNameAsc(ProductStatus status);
    
    List<Product> findByCategoryIdAndStatusOrderByNameAsc(Long categoryId, ProductStatus status);
    
    Page<Product> findByStatus(ProductStatus status, Pageable pageable);
    
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    
    Optional<Product> findBySku(String sku);
    
    boolean existsBySku(String sku);
    
    boolean existsBySkuAndIdNot(String sku, Long id);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.sku LIKE %:keyword%) " +
           "AND (:status IS NULL OR p.status = :status) " +
           "AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> searchProducts(@Param("keyword") String keyword, 
                                @Param("status") ProductStatus status,
                                @Param("categoryId") Long categoryId,
                                Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.minStockLevel AND p.minStockLevel IS NOT NULL")
    List<Product> findLowStockProducts();
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= 0")
    List<Product> findOutOfStockProducts();
    
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.status = :status")
    long countByStatus(@Param("status") ProductStatus status);
    
    @Query("SELECT p FROM Product p WHERE p.category.id IN :categoryIds")
    List<Product> findByCategoryIdIn(@Param("categoryIds") List<Long> categoryIds);
}