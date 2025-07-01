package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.FavoriteMenu;
import net.devgrr.springbootinit.entity.FavoriteMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 즐겨찾기 메뉴 데이터 접근 Repository 인터페이스
 * 
 * 즐겨찾기 메뉴 테이블(CM_FAVO_MENU)에 대한 데이터베이스 작업을 제공합니다.
 * 사용자별 즐겨찾기 메뉴 목록을 관리하며, 복합키를 사용합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface FavoriteMenuRepository extends JpaRepository<FavoriteMenu, FavoriteMenuId> {
    
    /**
     * 특정 사용자의 즐겨찾기 메뉴 목록을 정렬순으로 조회합니다.
     * 
     * @param userId 조회할 사용자 ID
     * @return List<FavoriteMenu> 정렬순으로 정렬된 즐겨찾기 메뉴 목록
     */
    List<FavoriteMenu> findByUserIdOrderBySortOrder(String userId);
    
    /**
     * 특정 사용자의 특정 메뉴가 즐겨찾기에 등록되어 있는지 확인합니다.
     * 
     * @param userId 확인할 사용자 ID
     * @param menuId 확인할 메뉴 ID
     * @return boolean 즐겨찾기에 등록되어 있으면 true
     */
    boolean existsByUserIdAndMenuId(String userId, String menuId);
    
    /**
     * 특정 사용자의 특정 메뉴를 즐겨찾기에서 삭제합니다.
     * 
     * @param userId 삭제할 사용자 ID
     * @param menuId 삭제할 메뉴 ID
     */
    void deleteByUserIdAndMenuId(String userId, String menuId);
    
    /**
     * 특정 사용자의 즐겨찾기 메뉴 개수를 조회합니다.
     * 
     * @param userId 조회할 사용자 ID
     * @return int 즐겨찾기 메뉴 개수
     */
    int countByUserId(String userId);
} 