package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.SystemProcessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 시스템 처리 로그 데이터 접근 Repository 인터페이스
 * 
 * 시스템 처리 로그 테이블(CM_SYS_PROC_LOG)에 대한 데이터베이스 작업을 제공합니다.
 * 시스템에서 발생하는 각종 처리 과정의 로그를 관리하며, 모니터링과 트러블슈팅에 활용됩니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface SystemProcessLogRepository extends JpaRepository<SystemProcessLog, String> {
    
    /**
     * 특정 프로그램 ID의 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param programId 조회할 프로그램 ID
     * @return List<SystemProcessLog> 최신순으로 정렬된 시스템 처리 로그 목록
     */
    List<SystemProcessLog> findByProgramIdOrderByRegisterDateDesc(String programId);
    
    /**
     * 특정 로그 코드의 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param logCode 조회할 로그 코드
     * @return List<SystemProcessLog> 최신순으로 정렬된 시스템 처리 로그 목록
     */
    List<SystemProcessLog> findByLogCodeOrderByRegisterDateDesc(String logCode);
    
    /**
     * 특정 기간의 시스템 처리 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param startDate 조회 시작 일시
     * @param endDate 조회 종료 일시
     * @return List<SystemProcessLog> 해당 기간의 시스템 처리 로그 목록
     */
    @Query("SELECT s FROM SystemProcessLog s WHERE s.registerDate BETWEEN :startDate AND :endDate ORDER BY s.registerDate DESC")
    List<SystemProcessLog> findByRegisterDateBetween(@Param("startDate") LocalDateTime startDate, 
                                                     @Param("endDate") LocalDateTime endDate);
    
    /**
     * 특정 프로그램 ID와 생성일자로 시스템 처리 로그를 조회합니다.
     * 
     * @param programId 조회할 프로그램 ID
     * @param createDate 조회할 생성일자
     * @return List<SystemProcessLog> 조건에 맞는 시스템 처리 로그 목록
     */
    @Query("SELECT s FROM SystemProcessLog s WHERE s.programId = :programId AND s.createDate = :createDate")
    List<SystemProcessLog> findByProgramIdAndCreateDate(@Param("programId") String programId, 
                                                        @Param("createDate") String createDate);
} 