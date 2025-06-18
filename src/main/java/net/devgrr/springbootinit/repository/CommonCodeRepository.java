package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CommonCode;
import net.devgrr.springbootinit.entity.CommonCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
    
    List<CommonCode> findByCodeGroupAndUseYnOrderBySortOrderAsc(CommonCodeGroup codeGroup, String useYn);
    
    List<CommonCode> findByCodeGroup_GroupCodeAndUseYnOrderBySortOrderAsc(String groupCode, String useYn);
    
    Optional<CommonCode> findByCodeGroup_GroupCodeAndCode(String groupCode, String code);
    
    Optional<CommonCode> findByCodeGroup_GroupCodeAndCodeAndUseYn(String groupCode, String code, String useYn);
    
    boolean existsByCodeGroup_GroupCodeAndCode(String groupCode, String code);
    
    boolean existsByCodeGroup_GroupCodeAndCodeAndIdNot(String groupCode, String code, Long id);
    
    @Query("SELECT c FROM CommonCode c WHERE c.codeGroup.groupCode = :groupCode AND (c.codeName LIKE %:keyword% OR c.description LIKE %:keyword%)")
    List<CommonCode> searchByGroupCodeAndKeyword(@Param("groupCode") String groupCode, @Param("keyword") String keyword);
    
    @Query("SELECT c FROM CommonCode c WHERE c.codeName LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<CommonCode> searchByKeyword(@Param("keyword") String keyword);
}