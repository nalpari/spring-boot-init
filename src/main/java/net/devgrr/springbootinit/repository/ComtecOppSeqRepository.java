package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.ComtecOppSeq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 시퀀스 관리 Repository
 * 
 * 시퀀스 관리 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 각 테이블별 시퀀스 번호 생성 및 관리 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface ComtecOppSeqRepository extends JpaRepository<ComtecOppSeq, String> {

    /**
     * 테이블명으로 시퀀스 정보를 조회합니다.
     * 
     * @param tableName 조회할 테이블명
     * @return 해당 테이블의 시퀀스 정보
     */
    Optional<ComtecOppSeq> findByTableName(String tableName);

    /**
     * 특정 테이블의 다음 ID 값을 증가시킵니다.
     * 
     * @param tableName 증가시킬 테이블명
     * @return 업데이트된 레코드 수
     */
    @Modifying
    @Query("UPDATE ComtecOppSeq c SET c.nextId = c.nextId + 1 WHERE c.tableName = :tableName")
    int incrementNextId(@Param("tableName") String tableName);

    /**
     * 특정 테이블의 다음 ID 값을 지정된 값만큼 증가시킵니다.
     * 
     * @param tableName 증가시킬 테이블명
     * @param increment 증가시킬 값
     * @return 업데이트된 레코드 수
     */
    @Modifying
    @Query("UPDATE ComtecOppSeq c SET c.nextId = c.nextId + :increment WHERE c.tableName = :tableName")
    int incrementNextIdBy(@Param("tableName") String tableName, @Param("increment") Long increment);

    /**
     * 특정 테이블의 시퀀스 정보가 존재하는지 확인합니다.
     * 
     * @param tableName 확인할 테이블명
     * @return 존재 여부
     */
    boolean existsByTableName(String tableName);

    /**
     * 특정 테이블의 현재 다음 ID 값을 조회합니다.
     * 
     * @param tableName 조회할 테이블명
     * @return 다음 ID 값
     */
    @Query("SELECT c.nextId FROM ComtecOppSeq c WHERE c.tableName = :tableName")
    Optional<Long> findNextIdByTableName(@Param("tableName") String tableName);

    /**
     * 특정 테이블의 다음 ID 값을 특정 값으로 설정합니다.
     * 
     * @param tableName 설정할 테이블명
     * @param nextId 설정할 다음 ID 값
     * @return 업데이트된 레코드 수
     */
    @Modifying
    @Query("UPDATE ComtecOppSeq c SET c.nextId = :nextId WHERE c.tableName = :tableName")
    int setNextId(@Param("tableName") String tableName, @Param("nextId") Long nextId);
} 