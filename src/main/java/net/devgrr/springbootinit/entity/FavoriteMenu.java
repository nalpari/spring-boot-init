package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 즐겨찾기 메뉴 엔티티
 * 
 * 사용자별로 자주 사용하는 메뉴를 즐겨찾기로 등록하여 관리하는 테이블입니다.
 * 사용자 ID와 메뉴 ID의 복합키를 사용하며, 사용자별 개인화된 메뉴 구성을 지원합니다.
 * 
 * 테이블명: CM_FAVO_MENU (Common Favorite Menu)
 * 
 * @Entity: JPA 엔티티임을 나타내는 어노테이션
 * @Table: 실제 데이터베이스 테이블명 매핑
 * @Data: Lombok - getter, setter, toString, equals, hashCode 자동 생성
 * @Builder: Lombok - 빌더 패턴 적용
 * @EntityListeners: JPA Auditing을 위한 리스너 설정
 * @IdClass: 복합키 클래스 지정
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024
 */
@Entity
@Table(name = "cm_favo_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@IdClass(FavoriteMenuId.class)
public class FavoriteMenu {

    /**
     * 사용자 ID (복합키 구성요소)
     * 즐겨찾기를 등록한 사용자의 고유 ID
     */
    @Id
    @Column(name = "user_id", length = 20)
    private String userId;

    /**
     * 메뉴 ID (복합키 구성요소)
     * 즐겨찾기로 등록된 메뉴의 고유 ID
     */
    @Id
    @Column(name = "menu_id", length = 20)
    private String menuId;

    /**
     * 부모 메뉴 ID
     * 즐겨찾기 메뉴의 상위 메뉴 ID
     */
    @Column(name = "parent_id", length = 30)
    private String parentId;

    /**
     * 메뉴명
     * 즐겨찾기 메뉴의 표시명 (캐시된 값)
     */
    @Column(name = "menu_nm", length = 4000)
    private String menuName;

    /**
     * 정렬순서
     * 사용자의 즐겨찾기 메뉴 목록에서의 표시 순서
     */
    @Column(name = "sort_ord")
    private Integer sortOrder;

    /**
     * 등록자 ID
     * 데이터를 최초 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 즐겨찾기 등록 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "regi_dt")
    @CreatedDate
    private LocalDateTime registerDate;

    /**
     * 수정자 ID
     * 데이터를 마지막으로 수정한 사용자 ID
     */
    @Column(name = "updt_id", length = 20)
    private String updateId;

    /**
     * 수정일시
     * 데이터 마지막 수정 일시 (JPA Auditing으로 자동 설정)
     */
    @Column(name = "updt_dt")
    @LastModifiedDate
    private LocalDateTime updateDate;

    /**
     * 메뉴 연관관계
     * 즐겨찾기로 등록된 실제 메뉴 정보 참조
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id", insertable = false, updatable = false)
    private Menu menu;
} 