package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 개인정보 접근 기록 엔티티
 * 
 * 개인정보보호법에 따른 개인정보 처리 및 접근 이력을 기록하는 테이블입니다.
 * 개인정보 접근, 수정, 삭제 등의 모든 활동을 추적하여
 * 개인정보보호 감사와 컴플라이언스 준수에 활용됩니다.
 * 
 * 테이블명: CM_INDVDLINFO_RCORD
 * 
 * @Entity: JPA 엔티티임을 나타내는 어노테이션
 * @Table: 실제 데이터베이스 테이블명 매핑
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @Builder: Lombok - 빌더 패턴 적용
 * @EntityListeners: JPA Auditing을 위한 리스너 설정
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "cm_indvdlinfo_rcord")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class IndividualInfoRecord {

    /**
     * 접근 번호 (기본키)
     * 개인정보 접근 기록을 식별하는 고유한 순번 (자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_no", length = 20)
    private Long accessNumber;

    /**
     * 사용자 ID
     * 개인정보에 접근한 사용자의 고유 ID
     */
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 접근 일시
     * 개인정보에 접근한 일시 (DATE 타입)
     */
    @Column(name = "access_dt")
    private LocalDateTime accessDate;

    /**
     * 접근 IP
     * 개인정보에 접근한 클라이언트의 IP 주소
     */
    @Column(name = "access_ip", length = 50)
    private String accessIp;

    /**
     * 접근 URL
     * 개인정보 접근 시 사용된 URL 정보
     */
    @Column(name = "access_url", length = 500)
    private String accessUrl;

    /**
     * 액션 타입 코드
     * 개인정보에 수행된 작업의 유형 (VIEW, CREATE, UPDATE, DELETE 등)
     */
    @Column(name = "action_type_cd", length = 25)
    private String actionTypeCode;

    /**
     * 액션 파라미터
     * 개인정보 접근 시 사용된 파라미터 정보
     */
    @Column(name = "action_param", length = 4000)
    private String actionParameter;

    /**
     * 등록일시
     * 개인정보 접근 기록 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 사용자 연관관계
     * 개인정보에 접근한 사용자 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;
} 