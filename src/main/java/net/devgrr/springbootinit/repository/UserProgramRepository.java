package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.UserProgram;
import net.devgrr.springbootinit.entity.UserProgramId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자별 프로그램 Repository 인터페이스
 * 
 * 사용자별 프로그램 권한 정보에 대한 데이터 접근을 담당하는 Repository입니다.
 * 개별 사용자에게 특별히 부여되는 프로그램 권한을 관리하며,
 * 역할 기반 권한과 함께 세밀한 권한 제어를 제공합니다.
 * 
 * 주요 기능:
 * - 사용자별 프로그램 권한 조회
 * - 프로그램별 권한 보유 사용자 조회
 * - 권한 유효기간 검증
 * - 특정 권한 레벨 확인
 * 
 * @Repository: Spring의 Repository 컴포넌트 등록
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface UserProgramRepository extends JpaRepository<UserProgram, UserProgramId> {

    /**
     * 특정 사용자의 모든 프로그램 권한 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자에게 할당된 프로그램 권한 목록
     */
    List<UserProgram> findByUserId(String userId);

    /**
     * 특정 프로그램에 권한이 있는 모든 사용자 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램에 권한이 있는 사용자 목록
     */
    List<UserProgram> findByProgramId(String programId);

    /**
     * 사용자와 프로그램의 권한 관계 존재 확인
     * 
     * @param userId 사용자 ID
     * @param programId 프로그램 ID
     * @return 권한 관계 존재 여부
     */
    boolean existsByUserIdAndProgramId(String userId, String programId);

    /**
     * 특정 사용자의 특정 프로그램 권한 조회
     * 
     * @param userId 사용자 ID
     * @param programId 프로그램 ID
     * @return 해당 사용자의 프로그램 권한 정보
     */
    UserProgram findByUserIdAndProgramId(String userId, String programId);

    /**
     * 특정 권한 레벨로 프로그램 권한 조회
     * 
     * @param roleAuthority 권한 레벨
     * @return 해당 권한 레벨의 프로그램 권한 목록
     */
    List<UserProgram> findByRoleAuthority(String roleAuthority);

    /**
     * 사용자 ID로 프로그램 정보와 함께 조회 (Join Fetch)
     * 
     * @param userId 사용자 ID
     * @return 프로그램 정보가 포함된 사용자 프로그램 목록
     */
    @Query("SELECT up FROM UserProgram up JOIN FETCH up.program WHERE up.userId = :userId")
    List<UserProgram> findByUserIdWithProgram(@Param("userId") String userId);

    /**
     * 프로그램 ID로 사용자 정보와 함께 조회 (Join Fetch)
     * 
     * @param programId 프로그램 ID
     * @return 사용자 정보가 포함된 사용자 프로그램 목록
     */
    @Query("SELECT up FROM UserProgram up JOIN FETCH up.user WHERE up.programId = :programId")
    List<UserProgram> findByProgramIdWithUser(@Param("programId") String programId);

    /**
     * 현재 유효한 사용자 프로그램 권한 조회 (유효기간 체크)
     * 
     * @param userId 사용자 ID
     * @param currentDate 현재 날짜 (YYYYMMDD 형식)
     * @return 현재 유효한 프로그램 권한 목록
     */
    @Query("SELECT up FROM UserProgram up WHERE up.userId = :userId " +
           "AND (up.validBeginDate IS NULL OR up.validBeginDate <= :currentDate) " +
           "AND (up.validEndDate IS NULL OR up.validEndDate >= :currentDate)")
    List<UserProgram> findValidUserPrograms(@Param("userId") String userId, @Param("currentDate") String currentDate);

    /**
     * 특정 사용자의 특정 권한 레벨 프로그램 목록 조회
     * 
     * @param userId 사용자 ID
     * @param roleAuthority 권한 레벨
     * @return 해당 조건의 프로그램 권한 목록
     */
    List<UserProgram> findByUserIdAndRoleAuthority(String userId, String roleAuthority);

    /**
     * 특정 프로그램의 특정 권한 레벨 사용자 목록 조회
     * 
     * @param programId 프로그램 ID
     * @param roleAuthority 권한 레벨
     * @return 해당 조건의 사용자 목록
     */
    List<UserProgram> findByProgramIdAndRoleAuthority(String programId, String roleAuthority);

    /**
     * 특정 사용자의 프로그램 권한 개수 조회
     * 
     * @param userId 사용자 ID
     * @return 사용자에게 할당된 프로그램 권한 개수
     */
    @Query("SELECT COUNT(up) FROM UserProgram up WHERE up.userId = :userId")
    long countByUserId(@Param("userId") String userId);

    /**
     * 특정 프로그램의 권한 보유 사용자 개수 조회
     * 
     * @param programId 프로그램 ID
     * @return 해당 프로그램에 권한이 있는 사용자 개수
     */
    @Query("SELECT COUNT(up) FROM UserProgram up WHERE up.programId = :programId")
    long countByProgramId(@Param("programId") String programId);

    /**
     * 사용자 ID로 사용자 프로그램 권한 삭제
     * 
     * @param userId 사용자 ID
     */
    void deleteByUserId(String userId);

    /**
     * 프로그램 ID로 사용자 프로그램 권한 삭제
     * 
     * @param programId 프로그램 ID
     */
    void deleteByProgramId(String programId);

    /**
     * 만료된 사용자 프로그램 권한 조회
     * 
     * @param currentDate 현재 날짜 (YYYYMMDD 형식)
     * @return 만료된 프로그램 권한 목록
     */
    @Query("SELECT up FROM UserProgram up WHERE up.validEndDate IS NOT NULL AND up.validEndDate < :currentDate")
    List<UserProgram> findExpiredUserPrograms(@Param("currentDate") String currentDate);
} 