package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.Role;
import net.devgrr.springbootinit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 데이터 접근 Repository 인터페이스
 * 
 * 사용자 엔티티에 대한 데이터베이스 CRUD 작업을 제공하는 Spring Data JPA Repository입니다.
 * JpaRepository를 상속받아 기본적인 CRUD 기능과 함께 사용자 맞춤 쿼리 메소드들을 정의합니다.
 * 
 * @Repository: Spring의 데이터 접근 계층 컴포넌트임을 나타내는 어노테이션
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 사용자명으로 사용자를 조회합니다.
     * 
     * @param username 조회할 사용자명
     * @return Optional<User> 사용자 정보 (존재하지 않으면 empty Optional)
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 이메일로 사용자를 조회합니다.
     * 
     * @param email 조회할 이메일 주소
     * @return Optional<User> 사용자 정보 (존재하지 않으면 empty Optional)
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 사용자명의 존재 여부를 확인합니다.
     * 
     * @param username 확인할 사용자명
     * @return boolean 사용자명이 존재하면 true
     */
    boolean existsByUsername(String username);
    
    /**
     * 이메일의 존재 여부를 확인합니다.
     * 
     * @param email 확인할 이메일 주소
     * @return boolean 이메일이 존재하면 true
     */
    boolean existsByEmail(String email);
    
    /**
     * 특정 ID를 제외하고 사용자명의 존재 여부를 확인합니다.
     * 사용자 정보 수정 시 본인을 제외한 중복 검사에 사용됩니다.
     * 
     * @param username 확인할 사용자명
     * @param id 제외할 사용자 ID
     * @return boolean 해당 ID를 제외하고 사용자명이 존재하면 true
     */
    boolean existsByUsernameAndIdNot(String username, Long id);
    
    /**
     * 특정 ID를 제외하고 이메일의 존재 여부를 확인합니다.
     * 사용자 정보 수정 시 본인을 제외한 중복 검사에 사용됩니다.
     * 
     * @param email 확인할 이메일 주소
     * @param id 제외할 사용자 ID
     * @return boolean 해당 ID를 제외하고 이메일이 존재하면 true
     */
    boolean existsByEmailAndIdNot(String email, Long id);
    
    /**
     * 특정 역할을 가진 사용자들을 조회합니다.
     * 
     * @param role 조회할 사용자 역할 (USER 또는 ADMIN)
     * @return List<User> 해당 역할을 가진 사용자 목록
     */
    List<User> findByRole(Role role);
    
    /**
     * 키워드로 사용자를 검색합니다.
     * 사용자명 또는 이메일에 키워드가 포함된 사용자들을 조회합니다.
     * 
     * @Query: JPQL을 사용한 커스텀 쿼리 정의
     * @param keyword 검색할 키워드
     * @return List<User> 키워드가 포함된 사용자 목록
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    List<User> searchByKeyword(@Param("keyword") String keyword);
}