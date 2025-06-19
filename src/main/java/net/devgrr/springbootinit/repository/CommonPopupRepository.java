package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CommonPopup;
import net.devgrr.springbootinit.entity.PopupType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommonPopupRepository extends JpaRepository<CommonPopup, Long> {

    List<CommonPopup> findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc();

    @Query("SELECT p FROM CommonPopup p WHERE p.isActive = true " +
           "AND p.displayStartDate <= :now AND p.displayEndDate >= :now " +
           "ORDER BY p.displayOrder ASC, p.createdAt DESC")
    List<CommonPopup> findActivePopupsAtDateTime(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM CommonPopup p WHERE p.isActive = true " +
           "AND p.displayStartDate <= :now AND p.displayEndDate >= :now " +
           "AND (:targetPage IS NULL OR p.targetPage IS NULL OR p.targetPage = :targetPage) " +
           "ORDER BY p.displayOrder ASC, p.createdAt DESC")
    List<CommonPopup> findActivePopupsForPage(@Param("now") LocalDateTime now, 
                                              @Param("targetPage") String targetPage);

    List<CommonPopup> findByPopupTypeAndIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(PopupType popupType);

    @Query("SELECT p FROM CommonPopup p WHERE p.isActive = true " +
           "AND (LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "ORDER BY p.displayOrder ASC, p.createdAt DESC")
    List<CommonPopup> searchByKeyword(@Param("keyword") String keyword);

    Page<CommonPopup> findByIsActiveTrueOrderByDisplayOrderAscCreatedAtDesc(Pageable pageable);

    @Query("SELECT COUNT(p) FROM CommonPopup p WHERE p.isActive = true " +
           "AND p.displayStartDate <= :now AND p.displayEndDate >= :now")
    long countActivePopups(@Param("now") LocalDateTime now);
}