package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 공통코드 데이터 접근 Repository 인터페이스
 * 
 * 공통코드 마스터 테이블(CM_CMON_CD)에 대한 데이터베이스 작업을 제공합니다.
 * 시스템에서 사용되는 각종 코드의 분류와 기본 정보를 관리하는 역할을 담당합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, String> {
    
    /**
     * 공통코드로 특정 공통코드를 조회합니다.
     * 
     * @param commonCode 조회할 공통코드
     * @return Optional<CommonCode> 공통코드 정보 (존재하지 않으면 empty Optional)
     */
    Optional<CommonCode> findByCommonCode(String commonCode);
    
    /**
     * 사용 여부에 따라 공통코드 목록을 정렬순으로 조회합니다.
     * 
     * @param useYn 사용 여부 ('Y' 또는 'N')
     * @return List<CommonCode> 정렬순으로 정렬된 공통코드 목록
     */
    List<CommonCode> findByUseYnOrderBySortOrder(String useYn);
    
    /**
     * 공통코드명에 특정 문자열이 포함되고 사용중인 공통코드를 조회합니다.
     * 
     * @param name 검색할 공통코드명의 일부
     * @return List<CommonCode> 조건에 맞는 공통코드 목록
     */
    @Query("SELECT c FROM CommonCode c WHERE c.commonCodeName LIKE %:name% AND c.useYn = 'Y'")
    List<CommonCode> findByCommonCodeNameContainingAndUseYn(@Param("name") String name);
    
    /**
     * 특정 공통코드의 존재 여부를 확인합니다.
     * 
     * @param commonCode 확인할 공통코드
     * @return boolean 공통코드가 존재하면 true
     */
    boolean existsByCommonCode(String commonCode);
} 