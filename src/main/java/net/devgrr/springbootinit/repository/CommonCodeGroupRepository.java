package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CommonCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroup, String> {
    
    List<CommonCodeGroup> findByUseYnOrderBySortOrderAsc(String useYn);
    
    @Query("SELECT g FROM CommonCodeGroup g WHERE g.groupName LIKE %:keyword% OR g.description LIKE %:keyword%")
    List<CommonCodeGroup> searchByKeyword(@Param("keyword") String keyword);
    
    Optional<CommonCodeGroup> findByGroupCodeAndUseYn(String groupCode, String useYn);
    
    boolean existsByGroupCode(String groupCode);
}