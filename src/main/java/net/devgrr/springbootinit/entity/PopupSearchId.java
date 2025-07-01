package net.devgrr.springbootinit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 팝업 검색 이력 복합키 클래스
 * 
 * PopupSearch 엔티티의 복합키를 정의하는 클래스입니다.
 * 팝업 ID와 순번으로 구성된 복합키를 통해 각 검색 이력을 고유하게 식별합니다.
 * 
 * JPA에서 복합키를 사용하기 위해서는 다음 조건을 만족해야 합니다:
 * - Serializable 인터페이스 구현
 * - 기본 생성자 존재
 * - equals()와 hashCode() 메서드 구현
 * - 모든 필드가 public이거나 public getter/setter 존재
 * 
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @NoArgsConstructor: Lombok - 기본 생성자 자동 생성
 * @AllArgsConstructor: Lombok - 모든 필드를 매개변수로 하는 생성자 자동 생성
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PopupSearchId implements Serializable {

    /**
     * 직렬화 버전 UID
     * 클래스의 버전 관리를 위한 고유 식별자
     */
    private static final long serialVersionUID = 1L;

    /**
     * 팝업 ID
     * 검색 대상 팝업의 식별자
     */
    private String popupId;

    /**
     * 순번
     * 동일 팝업에 대한 검색 이력의 순번
     */
    private Integer sequenceNumber;
} 