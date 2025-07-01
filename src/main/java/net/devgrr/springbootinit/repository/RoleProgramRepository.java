package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.RoleProgram;
import net.devgrr.springbootinit.entity.RoleProgramId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 역할 프로그램 매핑 리포지토리 인터페이스
 * 
 * RoleProgram 엔티티에 대한 데이터베이스 접근 기능을 제공하는 Repository 인터페이스입니다.
 * Spring Data JPA를 활용하여 RBAC(Role-Based Access Control) 시스템의 역할별 프로그램 권한 매핑을 관리하며,
 * 역할 기반 접근 제어, 권한 확인, 권한 통계 등의 기능을 지원합니다.
 * 
 * 주요 기능:
 * - 역할-프로그램 매핑 기본 CRUD 연산
 * - 역할별 접근 가능한 프로그램 목록 조회
 * - 프로그램별 접근 권한이 있는 역할 목록 조회
 * - 권한 유효성 검증
 * - 역할별/프로그램별 권한 통계
 * 
 * @Repository: Spring의 Repository 컴포넌트임을 나타내는 어노테이션
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface RoleProgramRepository extends JpaRepository<RoleProgram, RoleProgramId> {

    /**
     * 특정 역할의 프로그램 권한 목록 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 프로그램 권한 목록
     */
    List<RoleProgram> findByRoleCode(String roleCode);

    /**
     * 특정 프로그램의 역할 권한 목록 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램의 역할 권한 목록
     */
    List<RoleProgram> findByProgramId(String programId);

    /**
     * 특정 역할과 프로그램의 권한 매핑 조회
     * 
     * @param roleCode 역할 코드
     * @param programId 프로그램 ID
     * @return 해당 역할-프로그램 권한 매핑
     */
    Optional<RoleProgram> findByRoleCodeAndProgramId(String roleCode, String programId);

    /**
     * 사용 여부별 역할-프로그램 권한 목록 조회
     * 
     * @param useYn 사용 여부 ('Y': 사용, 'N': 미사용)
     * @return 해당 상태의 권한 목록
     */
    List<RoleProgram> findByUseYn(String useYn);

    /**
     * 특정 역할의 활성화된 프로그램 권한 목록 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 활성화된 프로그램 권한 목록
     */
    List<RoleProgram> findByRoleCodeAndUseYn(String roleCode, String useYn);

    /**
     * 특정 프로그램의 활성화된 역할 권한 목록 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램의 활성화된 역할 권한 목록
     */
    List<RoleProgram> findByProgramIdAndUseYn(String programId, String useYn);

    /**
     * 읽기 권한 여부별 권한 목록 조회
     * 
     * @param readYn 읽기 권한 여부 ('Y': 허용, 'N': 불허)
     * @return 해당 읽기 권한 상태의 권한 목록
     */
    List<RoleProgram> findByReadYn(String readYn);

    /**
     * 쓰기 권한 여부별 권한 목록 조회
     * 
     * @param writeYn 쓰기 권한 여부 ('Y': 허용, 'N': 불허)
     * @return 해당 쓰기 권한 상태의 권한 목록
     */
    List<RoleProgram> findByWriteYn(String writeYn);

    /**
     * 실행 권한 여부별 권한 목록 조회
     * 
     * @param executeYn 실행 권한 여부 ('Y': 허용, 'N': 불허)
     * @return 해당 실행 권한 상태의 권한 목록
     */
    List<RoleProgram> findByExecuteYn(String executeYn);

    /**
     * 삭제 권한 여부별 권한 목록 조회
     * 
     * @param deleteYn 삭제 권한 여부 ('Y': 허용, 'N': 불허)
     * @return 해당 삭제 권한 상태의 권한 목록
     */
    List<RoleProgram> findByDeleteYn(String deleteYn);

    /**
     * 특정 기간에 등록된 역할-프로그램 권한 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간에 등록된 권한 목록
     */
    List<RoleProgram> findByRegisterDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 기간에 수정된 역할-프로그램 권한 목록 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 해당 기간에 수정된 권한 목록
     */
    List<RoleProgram> findByModifyDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 역할의 권한 개수 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 총 권한 개수
     */
    long countByRoleCode(String roleCode);

    /**
     * 특정 프로그램의 권한 개수 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램의 총 권한 개수
     */
    long countByProgramId(String programId);

    /**
     * 특정 역할의 활성화된 권한 개수 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 활성화된 권한 개수
     */
    long countByRoleCodeAndUseYn(String roleCode, String useYn);

    /**
     * 특정 프로그램의 활성화된 권한 개수 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램의 활성화된 권한 개수
     */
    long countByProgramIdAndUseYn(String programId, String useYn);

    /**
     * 역할별 프로그램 권한 통계 조회
     * 
     * @return 역할별 프로그램 권한 통계 정보
     */
    @Query("SELECT rp.roleCode, " +
           "COUNT(*) as totalPrograms, " +
           "SUM(CASE WHEN rp.useYn = 'Y' THEN 1 ELSE 0 END) as activePrograms, " +
           "SUM(CASE WHEN rp.readYn = 'Y' THEN 1 ELSE 0 END) as readablePrograms, " +
           "SUM(CASE WHEN rp.writeYn = 'Y' THEN 1 ELSE 0 END) as writablePrograms, " +
           "SUM(CASE WHEN rp.executeYn = 'Y' THEN 1 ELSE 0 END) as executablePrograms, " +
           "SUM(CASE WHEN rp.deleteYn = 'Y' THEN 1 ELSE 0 END) as deletablePrograms " +
           "FROM RoleProgram rp " +
           "GROUP BY rp.roleCode " +
           "ORDER BY totalPrograms DESC")
    List<Object[]> findRoleProgramStatistics();

    /**
     * 프로그램별 역할 권한 통계 조회
     * 
     * @return 프로그램별 역할 권한 통계 정보
     */
    @Query("SELECT rp.programId, " +
           "COUNT(*) as totalRoles, " +
           "SUM(CASE WHEN rp.useYn = 'Y' THEN 1 ELSE 0 END) as activeRoles, " +
           "SUM(CASE WHEN rp.readYn = 'Y' THEN 1 ELSE 0 END) as readableRoles, " +
           "SUM(CASE WHEN rp.writeYn = 'Y' THEN 1 ELSE 0 END) as writableRoles, " +
           "SUM(CASE WHEN rp.executeYn = 'Y' THEN 1 ELSE 0 END) as executableRoles, " +
           "SUM(CASE WHEN rp.deleteYn = 'Y' THEN 1 ELSE 0 END) as deletableRoles " +
           "FROM RoleProgram rp " +
           "GROUP BY rp.programId " +
           "ORDER BY totalRoles DESC")
    List<Object[]> findProgramRoleStatistics();

    /**
     * 권한 타입별 통계 조회
     * 
     * @return 권한 타입별 통계 정보
     */
    @Query("SELECT 'READ' as permissionType, SUM(CASE WHEN rp.readYn = 'Y' THEN 1 ELSE 0 END) as grantedCount " +
           "FROM RoleProgram rp " +
           "UNION ALL " +
           "SELECT 'WRITE' as permissionType, SUM(CASE WHEN rp.writeYn = 'Y' THEN 1 ELSE 0 END) as grantedCount " +
           "FROM RoleProgram rp " +
           "UNION ALL " +
           "SELECT 'EXECUTE' as permissionType, SUM(CASE WHEN rp.executeYn = 'Y' THEN 1 ELSE 0 END) as grantedCount " +
           "FROM RoleProgram rp " +
           "UNION ALL " +
           "SELECT 'DELETE' as permissionType, SUM(CASE WHEN rp.deleteYn = 'Y' THEN 1 ELSE 0 END) as grantedCount " +
           "FROM RoleProgram rp")
    List<Object[]> findPermissionTypeStatistics();

    /**
     * 일별 권한 등록 통계 조회
     * 
     * @param startDate 시작 일시
     * @param endDate 종료 일시
     * @return 일별 권한 등록 통계 정보
     */
    @Query("SELECT DATE(rp.registerDate) as registerDate, " +
           "COUNT(*) as totalPermissions, " +
           "COUNT(DISTINCT rp.roleCode) as uniqueRoles, " +
           "COUNT(DISTINCT rp.programId) as uniquePrograms " +
           "FROM RoleProgram rp " +
           "WHERE rp.registerDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(rp.registerDate) " +
           "ORDER BY registerDate DESC")
    List<Object[]> findDailyPermissionStatistics(@Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    /**
     * 특정 역할의 읽기 권한이 있는 프로그램 목록 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 읽기 권한이 있는 프로그램 ID 목록
     */
    @Query("SELECT rp.programId FROM RoleProgram rp " +
           "WHERE rp.roleCode = :roleCode " +
           "AND rp.useYn = 'Y' " +
           "AND rp.readYn = 'Y'")
    List<String> findReadableProgramsByRole(@Param("roleCode") String roleCode);

    /**
     * 특정 역할의 쓰기 권한이 있는 프로그램 목록 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 쓰기 권한이 있는 프로그램 ID 목록
     */
    @Query("SELECT rp.programId FROM RoleProgram rp " +
           "WHERE rp.roleCode = :roleCode " +
           "AND rp.useYn = 'Y' " +
           "AND rp.writeYn = 'Y'")
    List<String> findWritableProgramsByRole(@Param("roleCode") String roleCode);

    /**
     * 특정 역할의 실행 권한이 있는 프로그램 목록 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 실행 권한이 있는 프로그램 ID 목록
     */
    @Query("SELECT rp.programId FROM RoleProgram rp " +
           "WHERE rp.roleCode = :roleCode " +
           "AND rp.useYn = 'Y' " +
           "AND rp.executeYn = 'Y'")
    List<String> findExecutableProgramsByRole(@Param("roleCode") String roleCode);

    /**
     * 특정 역할의 삭제 권한이 있는 프로그램 목록 조회
     * 
     * @param roleCode 역할 코드
     * @return 해당 역할의 삭제 권한이 있는 프로그램 ID 목록
     */
    @Query("SELECT rp.programId FROM RoleProgram rp " +
           "WHERE rp.roleCode = :roleCode " +
           "AND rp.useYn = 'Y' " +
           "AND rp.deleteYn = 'Y'")
    List<String> findDeletableProgramsByRole(@Param("roleCode") String roleCode);

    /**
     * 특정 프로그램에 읽기 권한이 있는 역할 목록 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램에 읽기 권한이 있는 역할 코드 목록
     */
    @Query("SELECT rp.roleCode FROM RoleProgram rp " +
           "WHERE rp.programId = :programId " +
           "AND rp.useYn = 'Y' " +
           "AND rp.readYn = 'Y'")
    List<String> findReadableRolesByProgram(@Param("programId") String programId);

    /**
     * 특정 프로그램에 쓰기 권한이 있는 역할 목록 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램에 쓰기 권한이 있는 역할 코드 목록
     */
    @Query("SELECT rp.roleCode FROM RoleProgram rp " +
           "WHERE rp.programId = :programId " +
           "AND rp.useYn = 'Y' " +
           "AND rp.writeYn = 'Y'")
    List<String> findWritableRolesByProgram(@Param("programId") String programId);

    /**
     * 권한이 없는 역할-프로그램 매핑 조회 (모든 권한이 'N'인 경우)
     * 
     * @return 모든 권한이 거부된 매핑 목록
     */
    @Query("SELECT rp FROM RoleProgram rp " +
           "WHERE rp.readYn = 'N' " +
           "AND rp.writeYn = 'N' " +
           "AND rp.executeYn = 'N' " +
           "AND rp.deleteYn = 'N'")
    List<RoleProgram> findPermissionsWithNoAccess();

    /**
     * 모든 권한이 있는 역할-프로그램 매핑 조회 (모든 권한이 'Y'인 경우)
     * 
     * @return 모든 권한이 허용된 매핑 목록
     */
    @Query("SELECT rp FROM RoleProgram rp " +
           "WHERE rp.useYn = 'Y' " +
           "AND rp.readYn = 'Y' " +
           "AND rp.writeYn = 'Y' " +
           "AND rp.executeYn = 'Y' " +
           "AND rp.deleteYn = 'Y'")
    List<RoleProgram> findPermissionsWithFullAccess();

    /**
     * 특정 역할의 특정 프로그램에 대한 권한 확인
     * 
     * @param roleCode 역할 코드
     * @param programId 프로그램 ID
     * @param permissionType 권한 타입 ('READ', 'WRITE', 'EXECUTE', 'DELETE')
     * @return 권한 여부
     */
    @Query("SELECT CASE " +
           "WHEN :permissionType = 'READ' THEN rp.readYn " +
           "WHEN :permissionType = 'write' THEN rp.writeYn " +
           "WHEN :permissionType = 'execute' THEN rp.executeYn " +
           "WHEN :permissionType = 'delete' THEN rp.deleteYn " +
           "ELSE 'N' END " +
           "FROM RoleProgram rp " +
           "WHERE rp.roleCode = :roleCode " +
           "AND rp.programId = :programId " +
           "AND rp.useYn = 'Y'")
    String checkPermission(@Param("roleCode") String roleCode,
                          @Param("programId") String programId,
                          @Param("permissionType") String permissionType);

    /**
     * 최근 등록된 역할-프로그램 권한 목록 조회
     * 
     * @param limit 조회할 개수
     * @return 최근 등록된 권한 목록
     */
    @Query("SELECT rp FROM RoleProgram rp " +
           "ORDER BY rp.registerDate DESC " +
           "LIMIT :limit")
    List<RoleProgram> findRecentPermissions(@Param("limit") int limit);

    /**
     * 최근 수정된 역할-프로그램 권한 목록 조회
     * 
     * @param limit 조회할 개수
     * @return 최근 수정된 권한 목록
     */
    @Query("SELECT rp FROM RoleProgram rp " +
           "WHERE rp.modifyDate IS NOT NULL " +
           "ORDER BY rp.modifyDate DESC " +
           "LIMIT :limit")
    List<RoleProgram> findRecentlyModifiedPermissions(@Param("limit") int limit);

    /**
     * 등록자별 권한 등록 통계 조회
     * 
     * @return 등록자별 권한 등록 통계 정보
     */
    @Query("SELECT rp.registerId, " +
           "COUNT(*) as totalPermissions, " +
           "COUNT(DISTINCT rp.roleCode) as uniqueRoles, " +
           "COUNT(DISTINCT rp.programId) as uniquePrograms, " +
           "MAX(rp.registerDate) as lastRegisterDate " +
           "FROM RoleProgram rp " +
           "WHERE rp.registerId IS NOT NULL " +
           "GROUP BY rp.registerId " +
           "ORDER BY totalPermissions DESC")
    List<Object[]> findRegisterStatistics();

    /**
     * 사용하지 않는 권한 매핑 조회
     * 
     * @return 사용하지 않는 권한 매핑 목록
     */
    @Query("SELECT rp FROM RoleProgram rp WHERE rp.useYn = 'N'")
    List<RoleProgram> findUnusedPermissions();

    /**
     * 특정 역할의 권한이 있는 프로그램 목록 조회 (페이징)
     * 
     * @param roleCode 역할 코드
     * @param pageable 페이징 정보
     * @return 해당 역할의 권한이 있는 프로그램 목록 (페이징)
     */
    @Query("SELECT rp FROM RoleProgram rp " +
           "WHERE rp.roleCode = :roleCode " +
           "AND rp.useYn = 'Y'")
    Page<RoleProgram> findActivePermissionsByRole(@Param("roleCode") String roleCode, Pageable pageable);
} 