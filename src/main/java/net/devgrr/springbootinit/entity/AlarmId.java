package net.devgrr.springbootinit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 알람 복합키 클래스
 * 
 * Alarm 엔티티의 복합키를 정의하는 클래스입니다.
 * 알람 사용자 ID와 알람 순번으로 구성된 복합키를 통해 각 알람을 고유하게 식별합니다.
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
public class AlarmId implements Serializable {

    /**
     * 직렬화 버전 UID
     * 클래스의 버전 관리를 위한 고유 식별자
     */
    private static final long serialVersionUID = 1L;

    /**
     * 알람 사용자 ID
     * 알람을 수신할 사용자의 ID
     */
    private String alarmUserId;

    /**
     * 알람 순번
     * 동일 사용자에 대한 알람의 순번
     */
    private Integer alarmSequence;
} 