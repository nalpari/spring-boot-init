package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메뉴 데이터 접근 Repository 인터페이스
 * 
 * 메뉴 테이블(CM_MENU)에 대한 데이터베이스 작업을 제공합니다.
 * 시스템의 메뉴 구조를 관리하며, 계층형 메뉴 구조를 지원합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    
    /**
     * 루트 메뉴들을 사용 여부에 따라 정렬순으로 조회합니다.
     * 부모ID가 null인 최상위 메뉴들을 반환합니다.
     * 
     * @param useYn 사용 여부 ('Y' 또는 'N')
     * @return List<Menu> 루트 메뉴 목록
     */
    List<Menu> findByParentIdIsNullAndUseYnOrderBySortOrder(String useYn);
    
    /**
     * 특정 부모 메뉴의 하위 메뉴들을 사용 여부에 따라 정렬순으로 조회합니다.
     * 
     * @param parentId 부모 메뉴 ID
     * @param useYn 사용 여부 ('Y' 또는 'N')
     * @return List<Menu> 하위 메뉴 목록
     */
    List<Menu> findByParentIdAndUseYnOrderBySortOrder(String parentId, String useYn);
    
    /**
     * 사용중인 루트 메뉴들을 정렬순으로 조회합니다.
     * 
     * @return List<Menu> 사용중인 루트 메뉴 목록
     */
    @Query("SELECT m FROM Menu m WHERE m.parentId IS NULL AND m.useYn = 'Y' ORDER BY m.sortOrder")
    List<Menu> findRootMenus();
    
    /**
     * 특정 부모 메뉴의 사용중인 하위 메뉴들을 정렬순으로 조회합니다.
     * 
     * @param parentId 부모 메뉴 ID
     * @return List<Menu> 사용중인 하위 메뉴 목록
     */
    @Query("SELECT m FROM Menu m WHERE m.parentId = :parentId AND m.useYn = 'Y' ORDER BY m.sortOrder")
    List<Menu> findChildMenus(@Param("parentId") String parentId);
    
    /**
     * 메뉴명에 특정 문자열이 포함되고 사용중인 메뉴들을 조회합니다.
     * 
     * @param name 검색할 메뉴명의 일부
     * @return List<Menu> 조건에 맞는 메뉴 목록
     */
    @Query("SELECT m FROM Menu m WHERE m.menuName LIKE %:name% AND m.useYn = 'Y'")
    List<Menu> findByMenuNameContaining(@Param("name") String name);
    
    /**
     * 특정 메뉴ID의 존재 여부를 확인합니다.
     * 
     * @param menuId 확인할 메뉴 ID
     * @return boolean 메뉴ID가 존재하면 true
     */
    boolean existsByMenuId(String menuId);
} 