package net.devgrr.springbootinit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * CSR 상세 테이블의 복합키 클래스
 * CM_CSR_D 테이블의 기본키는 REQ_DE, REQ_SEQ, PROC_SEQ로 구성된 복합키
 * 
 * JPA에서 복합키를 사용하기 위해서는 별도의 ID 클래스가 필요
 * @Data Lombok 어노테이션으로 equals, hashCode, toString 메소드 자동 생성
 * @NoArgsConstructor 기본 생성자 자동 생성
 * @AllArgsConstructor 모든 필드를 매개변수로 하는 생성자 자동 생성
 * Serializable 인터페이스를 구현하여 직렬화 가능하도록 설정
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsrDetailId implements Serializable {

    /**
     * 요청구분
     * CSR 요청의 구분 코드 (8자리)
     * 복합키의 첫 번째 구성요소
     */
    private String reqDe;

    /**
     * 요청순번
     * CSR 요청의 순차적 번호 (5자리)
     * 복합키의 두 번째 구성요소
     */
    private Integer reqSeq;

    /**
     * 처리순번
     * CSR 처리의 순차적 번호 (3자리)
     * 복합키의 세 번째 구성요소
     */
    private Integer procSeq;
} 