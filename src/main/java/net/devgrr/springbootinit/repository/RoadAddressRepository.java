package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.RoadAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 도로 주소 Repository
 * 
 * 도로 주소 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 도로명 주소 검색 및 관리를 위한 CRUD 및 검색 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface RoadAddressRepository extends JpaRepository<RoadAddress, String> {

    /**
     * 우편번호로 도로 주소 목록을 조회합니다.
     * 
     * @param zipCode 조회할 우편번호
     * @return 해당 우편번호의 도로 주소 목록
     */
    List<RoadAddress> findByZipCode(String zipCode);

    /**
     * 시군구명에 특정 문자열이 포함된 도로 주소들을 조회합니다.
     * 
     * @param siGunGu 검색할 시군구명 문자열
     * @return 조건에 맞는 도로 주소 목록
     */
    List<RoadAddress> findBySiGunGuContaining(String siGunGu);

    /**
     * 읍면동명에 특정 문자열이 포함된 도로 주소들을 조회합니다.
     * 
     * @param emd 검색할 읍면동명 문자열
     * @return 조건에 맞는 도로 주소 목록
     */
    List<RoadAddress> findByEmdContaining(String emd);

    /**
     * 도로명에 특정 문자열이 포함된 도로 주소들을 조회합니다.
     * 
     * @param roadName 검색할 도로명 문자열
     * @return 조건에 맞는 도로 주소 목록
     */
    List<RoadAddress> findByRoadNameContaining(String roadName);

    /**
     * 건물명에 특정 문자열이 포함된 도로 주소들을 조회합니다.
     * 
     * @param buildingName 검색할 건물명 문자열
     * @return 조건에 맞는 도로 주소 목록
     */
    List<RoadAddress> findByBuildingNameContaining(String buildingName);

    /**
     * 법정동 코드로 도로 주소 목록을 조회합니다.
     * 
     * @param legalDongCode 조회할 법정동 코드
     * @return 해당 법정동의 도로 주소 목록
     */
    List<RoadAddress> findByLegalDongCode(String legalDongCode);

    /**
     * 건물 관리번호로 도로 주소를 조회합니다.
     * 
     * @param buildingManagementNumber 조회할 건물 관리번호
     * @return 해당 건물의 도로 주소 정보
     */
    Optional<RoadAddress> findByBuildingManagementNumber(String buildingManagementNumber);

    /**
     * 지하 여부로 도로 주소 목록을 조회합니다.
     * 
     * @param undergroundYn 지하 여부 ('Y' 또는 'N')
     * @return 해당 조건의 도로 주소 목록
     */
    List<RoadAddress> findByUndergroundYn(String undergroundYn);

    /**
     * 산 여부로 도로 주소 목록을 조회합니다.
     * 
     * @param mountainYn 산 여부 ('Y' 또는 'N')
     * @return 해당 조건의 도로 주소 목록
     */
    List<RoadAddress> findByMountainYn(String mountainYn);

    /**
     * 복합 조건으로 도로 주소를 검색합니다.
     * 
     * @param siGunGu 시군구명
     * @param emd 읍면동명
     * @param roadName 도로명
     * @param pageable 페이징 정보
     * @return 조건에 맞는 도로 주소 목록
     */
    @Query("SELECT r FROM RoadAddress r WHERE " +
           "(:siGunGu IS NULL OR r.siGunGu LIKE %:siGunGu%) AND " +
           "(:emd IS NULL OR r.emd LIKE %:emd%) AND " +
           "(:roadName IS NULL OR r.roadName LIKE %:roadName%)")
    Page<RoadAddress> findByComplexConditions(@Param("siGunGu") String siGunGu,
                                              @Param("emd") String emd,
                                              @Param("roadName") String roadName,
                                              Pageable pageable);

    /**
     * 우편번호와 도로명으로 도로 주소를 조회합니다.
     * 
     * @param zipCode 우편번호
     * @param roadName 도로명
     * @return 조건에 맞는 도로 주소 목록
     */
    List<RoadAddress> findByZipCodeAndRoadNameContaining(String zipCode, String roadName);

    /**
     * 건물 본번과 부번으로 도로 주소를 조회합니다.
     * 
     * @param buildingMainNumber 건물 본번
     * @param buildingSubNumber 건물 부번
     * @return 조건에 맞는 도로 주소 목록
     */
    List<RoadAddress> findByBuildingMainNumberAndBuildingSubNumber(String buildingMainNumber, String buildingSubNumber);
} 