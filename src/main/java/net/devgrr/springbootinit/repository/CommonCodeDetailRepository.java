package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CommonCodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 공통코드 상세 데이터 접근 Repository 인터페이스
 * 
 * 공통코드 상세 테이블(CM_CMON_CD_D)에 대한 데이터베이스 작업을 제공합니다.
 * 공통코드에 속하는 실제 코드 값들과 상세 정보를 관리하는 역할을 담당합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface CommonCodeDetailRepository extends JpaRepository<CommonCodeDetail, String> {
    
    /**
     * 특정 공통코드에 속하는 상세 코드들을 정렬순으로 조회합니다.
     * 
     * @param commonCode 조회할 공통코드
     * @return List<CommonCodeDetail> 정렬순으로 정렬된 상세 코드 목록
     */
    List<CommonCodeDetail> findByCommonCodeOrderBySortOrder(String commonCode);
    
    /**
     * 특정 공통코드와 사용 여부에 따라 상세 코드들을 정렬순으로 조회합니다.
     * 
     * @param commonCode 조회할 공통코드
     * @param useYn 사용 여부 ('Y' 또는 'N')
     * @return List<CommonCodeDetail> 조건에 맞는 상세 코드 목록
     */
    List<CommonCodeDetail> findByCommonCodeAndUseYnOrderBySortOrder(String commonCode, String useYn);
    
    /**
     * 특정 공통코드에 속하고 사용중인 상세 코드들을 조회합니다.
     * 
     * @param commonCode 조회할 공통코드
     * @return List<CommonCodeDetail> 사용중인 상세 코드 목록
     */
    @Query("SELECT d FROM CommonCodeDetail d WHERE d.commonCode = :commonCode AND d.useYn = 'Y' ORDER BY d.sortOrder")
    List<CommonCodeDetail> findActiveDetailsByCommonCode(@Param("commonCode") String commonCode);
    
    /**
     * 특정 공통코드와 코드ID의 존재 여부를 확인합니다.
     * 
     * @param commonCode 확인할 공통코드
     * @param codeId 확인할 코드ID
     * @return boolean 해당 조합이 존재하면 true
     */
    boolean existsByCommonCodeAndCodeId(String commonCode, String codeId);
} 