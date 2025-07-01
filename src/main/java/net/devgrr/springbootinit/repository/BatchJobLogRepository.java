package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.BatchJobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 배치 작업 로그 데이터 접근 Repository 인터페이스
 * 
 * 배치 작업 로그 테이블(CM_BATCH_JOB_LOG)에 대한 데이터베이스 작업을 제공합니다.
 * 시스템에서 실행되는 배치 작업의 실행 기록과 결과를 관리하며, 배치 작업 모니터링에 활용됩니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface BatchJobLogRepository extends JpaRepository<BatchJobLog, String> {
    
    /**
     * 특정 프로그램 ID의 배치 작업 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param programId 조회할 프로그램 ID
     * @return List<BatchJobLog> 최신순으로 정렬된 배치 작업 로그 목록
     */
    List<BatchJobLog> findByProgramIdOrderByRegisterDateDesc(String programId);
    
    /**
     * 특정 작업일자의 배치 작업 로그를 작업순서번호 역순으로 조회합니다.
     * 
     * @param workDate 조회할 작업일자
     * @return List<BatchJobLog> 작업순서번호 역순으로 정렬된 배치 작업 로그 목록
     */
    List<BatchJobLog> findByWorkDateOrderByWorkSequenceNumberDesc(String workDate);
    
    /**
     * 특정 작업 결과의 배치 작업 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param result 조회할 작업 결과 ('SUCCESS', 'FAIL' 등)
     * @return List<BatchJobLog> 해당 결과의 배치 작업 로그 목록
     */
    @Query("SELECT b FROM BatchJobLog b WHERE b.workResult = :result ORDER BY b.registerDate DESC")
    List<BatchJobLog> findByWorkResult(@Param("result") String result);
    
    /**
     * 특정 기간의 배치 작업 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param startDate 조회 시작 일시
     * @param endDate 조회 종료 일시
     * @return List<BatchJobLog> 해당 기간의 배치 작업 로그 목록
     */
    @Query("SELECT b FROM BatchJobLog b WHERE b.registerDate BETWEEN :startDate AND :endDate ORDER BY b.registerDate DESC")
    List<BatchJobLog> findByRegisterDateBetween(@Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    /**
     * 특정 프로그램 ID와 작업일자로 배치 작업 로그를 조회합니다.
     * 
     * @param programId 조회할 프로그램 ID
     * @param workDate 조회할 작업일자
     * @return List<BatchJobLog> 조건에 맞는 배치 작업 로그 목록
     */
    @Query("SELECT b FROM BatchJobLog b WHERE b.programId = :programId AND b.workDate = :workDate")
    List<BatchJobLog> findByProgramIdAndWorkDate(@Param("programId") String programId, 
                                                @Param("workDate") String workDate);
} 