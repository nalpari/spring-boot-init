package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 프로그램 Repository 인터페이스
 * 
 * 시스템 프로그램 정보에 대한 데이터 접근을 담당하는 Repository입니다.
 * 프로그램 메타데이터 관리, 권한 검증, 프로그램 목록 제공 등의 기능을 제공합니다.
 * 
 * 주요 기능:
 * - 프로그램 기본 CRUD 기능
 * - 프로그램 타입별 조회
 * - 권한별 프로그램 조회
 * - 앱 그룹별 프로그램 조회
 * - 프로그램 검색 기능
 * 
 * @Repository: Spring의 Repository 컴포넌트 등록
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface ProgramRepository extends JpaRepository<Program, String> {

    /**
     * 프로그램 파일명으로 프로그램 조회
     * 
     * @param programFile 프로그램 파일명
     * @return 프로그램 정보
     */
    Optional<Program> findByProgramFile(String programFile);

    /**
     * 앱 그룹별 프로그램 목록 조회
     * 
     * @param appGroup 앱 그룹
     * @return 해당 앱 그룹의 프로그램 목록
     */
    List<Program> findByAppGroup(String appGroup);

    /**
     * 프로그램 타입별 프로그램 목록 조회
     * 
     * @param programType 프로그램 타입
     * @return 해당 타입의 프로그램 목록
     */
    List<Program> findByProgramType(String programType);

    /**
     * 권한별 프로그램 목록 조회
     * 
     * @param authority 권한
     * @return 해당 권한의 프로그램 목록
     */
    List<Program> findByAuthority(String authority);

    /**
     * 종료 여부별 프로그램 목록 조회
     * 
     * @param closeYn 종료 여부 ('Y' 또는 'N')
     * @return 해당 상태의 프로그램 목록
     */
    List<Program> findByCloseYn(String closeYn);

    /**
     * 메뉴 패스워드 사용여부별 프로그램 목록 조회
     * 
     * @param menuPasswordUseYn 메뉴 패스워드 사용여부 ('Y' 또는 'N')
     * @return 해당 설정의 프로그램 목록
     */
    List<Program> findByMenuPasswordUseYn(String menuPasswordUseYn);

    /**
     * 운영중인 프로그램 목록 조회 (종료되지 않은 프로그램)
     * 
     * @return 운영중인 프로그램 목록
     */
    @Query("SELECT p FROM Program p WHERE p.closeYn = 'N' OR p.closeYn IS NULL")
    List<Program> findActivePrograms();

    /**
     * 특정 앱 그룹의 운영중인 프로그램 목록 조회
     * 
     * @param appGroup 앱 그룹
     * @return 해당 앱 그룹의 운영중인 프로그램 목록
     */
    @Query("SELECT p FROM Program p WHERE p.appGroup = :appGroup AND (p.closeYn = 'N' OR p.closeYn IS NULL)")
    List<Program> findActiveProgramsByAppGroup(@Param("appGroup") String appGroup);

    /**
     * 프로그램 ID 또는 프로그램 파일명으로 검색
     * 
     * @param keyword 검색 키워드
     * @return 검색된 프로그램 목록
     */
    @Query("SELECT p FROM Program p WHERE p.programId LIKE %:keyword% OR p.programFile LIKE %:keyword%")
    List<Program> searchByProgramIdOrFile(@Param("keyword") String keyword);

    /**
     * URL로 프로그램 조회
     * 
     * @param url URL
     * @return 프로그램 정보
     */
    Optional<Program> findByUrl(String url);

    /**
     * 내부 URL로 프로그램 조회
     * 
     * @param innerUrl 내부 URL
     * @return 프로그램 정보
     */
    Optional<Program> findByInnerUrl(String innerUrl);

    /**
     * 프로그램 타입과 권한으로 프로그램 목록 조회
     * 
     * @param programType 프로그램 타입
     * @param authority 권한
     * @return 조건에 맞는 프로그램 목록
     */
    List<Program> findByProgramTypeAndAuthority(String programType, String authority);

    /**
     * 앱 그룹과 프로그램 타입으로 프로그램 목록 조회
     * 
     * @param appGroup 앱 그룹
     * @param programType 프로그램 타입
     * @return 조건에 맞는 프로그램 목록
     */
    List<Program> findByAppGroupAndProgramType(String appGroup, String programType);

    /**
     * 프로그램 ID 존재 여부 확인
     * 
     * @param programId 프로그램 ID
     * @return 존재 여부
     */
    boolean existsByProgramId(String programId);

    /**
     * 프로그램 파일명 존재 여부 확인
     * 
     * @param programFile 프로그램 파일명
     * @return 존재 여부
     */
    boolean existsByProgramFile(String programFile);

    /**
     * 특정 앱 그룹의 프로그램 개수 조회
     * 
     * @param appGroup 앱 그룹
     * @return 프로그램 개수
     */
    @Query("SELECT COUNT(p) FROM Program p WHERE p.appGroup = :appGroup")
    long countByAppGroup(@Param("appGroup") String appGroup);

    /**
     * 특정 권한의 프로그램 개수 조회
     * 
     * @param authority 권한
     * @return 프로그램 개수
     */
    @Query("SELECT COUNT(p) FROM Program p WHERE p.authority = :authority")
    long countByAuthority(@Param("authority") String authority);
} 