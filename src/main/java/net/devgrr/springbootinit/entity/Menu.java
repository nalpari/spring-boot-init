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
import java.util.List;

/**
 * 메뉴 엔티티
 * 
 * 시스템의 메뉴 구조를 관리하는 테이블입니다.
 * 계층형 구조를 지원하여 부모-자식 관계의 메뉴 트리를 구성할 수 있습니다.
 * 사용자의 네비게이션과 권한 관리의 기초가 되는 중요한 엔티티입니다.
 * 
 * 테이블명: CM_MENU (Common Menu)
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
@Table(name = "cm_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    /**
     * 메뉴 ID (기본키)
     * 메뉴를 식별하는 고유한 키값
     */
    @Id
    @Column(name = "menu_id", length = 30)
    private String menuId;

    /**
     * 부모 메뉴 ID
     * 상위 메뉴의 ID (최상위 메뉴인 경우 null)
     */
    @Column(name = "parent_id", length = 30)
    private String parentId;

    /**
     * 메뉴명
     * 화면에 표시되는 메뉴의 이름
     */
    @Column(name = "menu_nm", length = 4000)
    private String menuName;

    /**
     * 정렬순서
     * 같은 레벨 메뉴들 간의 표시 순서
     */
    @Column(name = "sort_ord")
    private Integer sortOrder;

    /**
     * 사용여부
     * 'Y': 사용, 'N': 미사용
     */
    @Column(name = "use_yn", length = 1)
    private String useYn;

    /**
     * 메뉴 타입
     * 메뉴의 유형을 구분하는 코드 (예: MENU, BUTTON, LINK 등)
     */
    @Column(name = "menu_type", length = 5)
    private String menuType;

    /**
     * 비고
     * 메뉴에 대한 추가적인 설명이나 메모
     */
    @Column(name = "rmrk", length = 4000)
    private String remark;

    /**
     * 등록자 ID
     * 데이터를 최초 등록한 사용자 ID
     */
    @Column(name = "regi_id", length = 20)
    private String registerId;

    /**
     * 등록일시
     * 데이터 최초 등록 일시 (JPA Auditing으로 자동 설정)
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
     * 부모 메뉴 연관관계
     * 자기 참조를 통한 계층형 구조 구현
     * @ManyToOne: 다대일 관계 매핑
     * fetch = FetchType.LAZY: 지연 로딩 설정
     * insertable/updatable = false: 읽기 전용 관계
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "menu_id", insertable = false, updatable = false)
    private Menu parentMenu;

    /**
     * 하위 메뉴 목록
     * 이 메뉴의 자식 메뉴들
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 양방향 관계에서 주인이 아닌 쪽 명시
     * cascade = CascadeType.ALL: 부모 변경 시 자식도 함께 처리
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> childMenus;

    /**
     * 즐겨찾기 메뉴 목록
     * 이 메뉴를 즐겨찾기로 등록한 사용자들의 목록
     * @OneToMany: 일대다 관계 매핑
     * mappedBy: 양방향 관계에서 주인이 아닌 쪽 명시
     * cascade = CascadeType.ALL: 부모 변경 시 자식도 함께 처리
     * fetch = FetchType.LAZY: 지연 로딩 설정
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FavoriteMenu> favoriteMenus;
} 