package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 접근 로그 데이터 접근 Repository 인터페이스
 * 
 * 접근 로그 테이블(CM_ACCES_LOG)에 대한 데이터베이스 작업을 제공합니다.
 * 시스템에 대한 사용자 접근 기록을 관리하며, 보안 모니터링과 사용 패턴 분석에 활용됩니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    
    /**
     * 특정 접근 IP의 접근 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param accessIp 조회할 접근 IP 주소
     * @return List<AccessLog> 최신순으로 정렬된 접근 로그 목록
     */
    List<AccessLog> findByAccessIpOrderByRegisterDateDesc(String accessIp);
    
    /**
     * 특정 프로그램 ID의 접근 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param programId 조회할 프로그램 ID
     * @return List<AccessLog> 최신순으로 정렬된 접근 로그 목록
     */
    List<AccessLog> findByProgramIdOrderByRegisterDateDesc(String programId);
    
    /**
     * 특정 기간의 접근 로그를 등록일시 역순으로 조회합니다.
     * 
     * @param startDate 조회 시작 일시
     * @param endDate 조회 종료 일시
     * @return List<AccessLog> 해당 기간의 접근 로그 목록
     */
    @Query("SELECT a FROM AccessLog a WHERE a.registerDate BETWEEN :startDate AND :endDate ORDER BY a.registerDate DESC")
    List<AccessLog> findByRegisterDateBetween(@Param("startDate") LocalDateTime startDate, 
                                             @Param("endDate") LocalDateTime endDate);
    
    /**
     * 특정 접근 IP와 접근일자로 접근 로그를 조회합니다.
     * 
     * @param accessIp 조회할 접근 IP 주소
     * @param accessDate 조회할 접근일자
     * @return List<AccessLog> 조건에 맞는 접근 로그 목록
     */
    @Query("SELECT a FROM AccessLog a WHERE a.accessIp = :accessIp AND a.accessDate = :accessDate")
    List<AccessLog> findByAccessIpAndAccessDate(@Param("accessIp") String accessIp, 
                                               @Param("accessDate") String accessDate);
    
    /**
     * 특정 접근 IP의 총 접근 횟수를 조회합니다.
     * 
     * @param accessIp 조회할 접근 IP 주소
     * @return long 해당 IP의 총 접근 횟수
     */
    @Query("SELECT COUNT(a) FROM AccessLog a WHERE a.accessIp = :accessIp")
    long countByAccessIp(@Param("accessIp") String accessIp);
} 