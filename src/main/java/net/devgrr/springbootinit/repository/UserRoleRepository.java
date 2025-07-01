package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.UserRole;
import net.devgrr.springbootinit.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 역할 Repository 인터페이스
 * 
 * 사용자별 역할 할당 정보에 대한 데이터 접근을 담당하는 Repository입니다.
 * RBAC(Role-Based Access Control) 시스템의 핵심 구성요소로,
 * 사용자와 역할 간의 매핑 관계를 관리합니다.
 * 
 * 주요 기능:
 * - 사용자별 역할 조회
 * - 역할별 사용자 조회
 * - 사용자 역할 존재 확인
 * - 특정 권한 체크
 * 
 * @Repository: Spring의 Repository 컴포넌트 등록
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    /**
     * 특정 사용자의 모든 역할 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자에게 할당된 역할 목록
     */
    List<UserRole> findByUserId(String userId);

    /**
     * 특정 역할을 가진 모든 사용자 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할을 가진 사용자 목록
     */
    List<UserRole> findByRoleCode(String roleCode);

    /**
     * 사용자와 역할의 매핑 관계 존재 확인
     * 
     * @param userId 사용자 ID
     * @param roleCode 역할 코드
     * @return 매핑 관계 존재 여부
     */
    boolean existsByUserIdAndRoleCode(String userId, String roleCode);

    /**
     * 특정 사용자가 특정 역할을 가지고 있는지 확인
     * 
     * @param userId 사용자 ID
     * @param roleCode 역할 코드
     * @return 해당 사용자의 역할 정보 (존재하지 않으면 null)
     */
    UserRole findByUserIdAndRoleCode(String userId, String roleCode);

    /**
     * 사용자 ID로 역할 정보와 함께 조회 (Join Fetch)
     * 
     * @param userId 사용자 ID
     * @return 역할 정보가 포함된 사용자 역할 목록
     */
    @Query("SELECT ur FROM UserRole ur JOIN FETCH ur.role WHERE ur.userId = :userId")
    List<UserRole> findByUserIdWithRole(@Param("userId") String userId);

    /**
     * 역할 코드로 사용자 정보와 함께 조회 (Join Fetch)
     * 
     * @param roleCode 역할 코드
     * @return 사용자 정보가 포함된 사용자 역할 목록
     */
    @Query("SELECT ur FROM UserRole ur JOIN FETCH ur.user WHERE ur.roleCode = :roleCode")
    List<UserRole> findByRoleCodeWithUser(@Param("roleCode") String roleCode);

    /**
     * 특정 사용자의 역할 개수 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자에게 할당된 역할 개수
     */
    @Query("SELECT COUNT(ur) FROM UserRole ur WHERE ur.userId = :userId")
    long countByUserId(@Param("userId") String userId);

    /**
     * 특정 역할을 가진 사용자 개수 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할을 가진 사용자 개수
     */
    @Query("SELECT COUNT(ur) FROM UserRole ur WHERE ur.roleCode = :roleCode")
    long countByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 사용자별 역할 목록을 역할명과 함께 조회
     * 
     * @param userId 사용자 ID
     * @return 역할 코드와 역할명이 포함된 리스트
     */
    @Query("SELECT ur.roleCode, r.roleName FROM UserRole ur JOIN ur.role r WHERE ur.userId = :userId")
    List<Object[]> findRoleCodeAndNameByUserId(@Param("userId") String userId);

    /**
     * 사용자 ID로 사용자 역할 삭제
     * 
     * @param userId 사용자 ID
     */
    void deleteByUserId(String userId);

    /**
     * 역할 코드로 사용자 역할 삭제
     * 
     * @param roleCode 역할 코드
     */
    void deleteByRoleCode(String roleCode);
} 