package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByIsActiveTrueOrderByDisplayOrderAsc();
    
    List<Category> findByParentIsNullAndIsActiveTrueOrderByDisplayOrderAsc();
    
    List<Category> findByParentIdAndIsActiveTrueOrderByDisplayOrderAsc(Long parentId);
    
    @Query("SELECT c FROM Category c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Category> searchByKeyword(@Param("keyword") String keyword);
    
    Optional<Category> findByIdAndIsActiveTrue(Long id);
    
    boolean existsByNameAndParentId(String name, Long parentId);
    
    boolean existsByNameAndParentIdAndIdNot(String name, Long parentId, Long id);
}