package net.devgrr.springbootinit.repository;

import net.devgrr.springbootinit.entity.WordDictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 단어 사전 Repository
 * 
 * 단어 사전 엔티티에 대한 데이터베이스 액세스를 담당하는 Repository 인터페이스입니다.
 * 다국어 단어 사전 관리를 위한 CRUD 및 검색 기능을 제공합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface WordDictionaryRepository extends JpaRepository<WordDictionary, String> {

    /**
     * 영어 단어명으로 단어 사전 정보를 조회합니다.
     * 
     * @param englishName 조회할 영어 단어명
     * @return 해당 영어 단어의 사전 정보
     */
    Optional<WordDictionary> findByEnglishName(String englishName);

    /**
     * 한국어 단어명에 특정 문자열이 포함된 단어들을 조회합니다.
     * 
     * @param koreanName 검색할 한국어 문자열
     * @return 조건에 맞는 단어 목록
     */
    List<WordDictionary> findByKoreanNameContaining(String koreanName);

    /**
     * 영어 단어명에 특정 문자열이 포함된 단어들을 조회합니다.
     * 
     * @param englishName 검색할 영어 문자열
     * @return 조건에 맞는 단어 목록
     */
    List<WordDictionary> findByEnglishNameContaining(String englishName);

    /**
     * 출처별로 단어 목록을 조회합니다.
     * 
     * @param source 조회할 출처
     * @param pageable 페이징 정보
     * @return 해당 출처의 단어 목록
     */
    Page<WordDictionary> findBySource(String source, Pageable pageable);

    /**
     * 영어 약어로 단어를 조회합니다.
     * 
     * @param englishAbbreviation 조회할 영어 약어
     * @return 해당 약어의 단어 목록
     */
    List<WordDictionary> findByEnglishAbbreviation(String englishAbbreviation);

    /**
     * 단어 설명에 특정 키워드가 포함된 단어들을 조회합니다.
     * 
     * @param keyword 검색할 키워드
     * @return 조건에 맞는 단어 목록
     */
    @Query("SELECT w FROM WordDictionary w WHERE w.wordDescription LIKE %:keyword%")
    List<WordDictionary> findByWordDescriptionContaining(@Param("keyword") String keyword);

    /**
     * 한국어 또는 영어 단어명으로 검색합니다.
     * 
     * @param searchTerm 검색어
     * @return 조건에 맞는 단어 목록
     */
    @Query("SELECT w FROM WordDictionary w WHERE w.koreanName LIKE %:searchTerm% OR w.englishName LIKE %:searchTerm%")
    List<WordDictionary> findByKoreanNameOrEnglishNameContaining(@Param("searchTerm") String searchTerm);
} 