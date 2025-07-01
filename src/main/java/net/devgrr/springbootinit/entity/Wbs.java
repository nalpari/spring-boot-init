package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * WBS(Work Breakdown Structure) 엔티티
 * 작업분할구조 정보를 관리하는 테이블
 * 
 * @Entity JPA 엔티티로 선언
 * @Table 데이터베이스 테이블 이름을 CM_WBS로 지정
 * @EntityListeners JPA Auditing 기능을 사용하여 생성/수정 시간 자동 관리
 */
@Entity
@Table(name = "CM_WBS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Wbs {

    /**
     * WBS 번호 (기본키)
     * 5자리 숫자로 구성된 WBS 고유 번호
     */
    @Id
    @Column(name = "WBS_NO", precision = 5, nullable = false)
    private Integer wbsNo;

    /**
     * 레벨 코드
     * WBS의 계층 레벨을 나타내는 코드 (5자리)
     */
    @Column(name = "LEVEL_CD", length = 5)
    private String levelCd;

    /**
     * 구분
     * WBS 항목의 구분을 나타내는 코드 (2자리)
     */
    @Column(name = "DIV", length = 2)
    private String div;

    /**
     * 메뉴1
     * 첫 번째 메뉴 분류 (50자리)
     */
    @Column(name = "MENU1", length = 50)
    private String menu1;

    /**
     * 메뉴2
     * 두 번째 메뉴 분류 (50자리)
     */
    @Column(name = "MENU2", length = 50)
    private String menu2;

    /**
     * 메뉴3
     * 세 번째 메뉴 분류 (50자리)
     */
    @Column(name = "MENU3", length = 50)
    private String menu3;

    /**
     * 프로그램명
     * WBS와 연관된 프로그램의 이름 (4000자리)
     */
    @Column(name = "PROG_NM", length = 4000)
    private String progNm;

    /**
     * 프로그램 ID
     * WBS와 연관된 프로그램의 식별자 (50자리)
     */
    @Column(name = "PROG_ID", length = 50)
    private String progId;

    /**
     * 프로그램 타입
     * 프로그램의 종류를 나타내는 타입 (5자리)
     */
    @Column(name = "PROG_TYPE", length = 5)
    private String progType;

    /**
     * 개발 일수
     * 개발에 소요되는 예상 일수 (4자리, 소수점 1자리)
     */
    @Column(name = "DEV_DAY", precision = 4, scale = 1)
    private Double devDay;

    /**
     * 진행 상태
     * 현재 진행 상태를 나타내는 코드 (5자리)
     */
    @Column(name = "PRGS_STAT", length = 5)
    private String prgsStat;

    /**
     * 우선순위
     * 작업의 우선순위 (5자리)
     */
    @Column(name = "PRIO", length = 5)
    private String prio;

    /**
     * 계획 레벨
     * 계획의 단계 레벨 (10자리)
     */
    @Column(name = "PL", length = 10)
    private String pl;

    /**
     * 복사 설계자
     * 복사 작업의 설계자 (10자리)
     */
    @Column(name = "CCPY_DSGNR", length = 10)
    private String ccpyDsgnr;

    /**
     * 복사 개발자
     * 복사 작업의 개발자 (10자리)
     */
    @Column(name = "CCPY_DEVPR", length = 10)
    private String ccpyDevpr;

    /**
     * 복사 계획 시작일
     * 복사 작업의 계획된 시작 날짜 (8자리)
     */
    @Column(name = "CCPY_PLAN_START_DT", length = 8)
    private String ccpyPlanStartDt;

    /**
     * 복사 계획 종료일
     * 복사 작업의 계획된 종료 날짜 (8자리)
     */
    @Column(name = "CCPY_PLAN_END_DT", length = 8)
    private String ccpyPlanEndDt;

    /**
     * 복사 계획 변경일
     * 복사 계획이 변경된 날짜 (8자리)
     */
    @Column(name = "CCPY_PLAN_CHG_DT", length = 8)
    private String ccpyPlanChgDt;

    /**
     * 복사 결과 시작일
     * 복사 작업의 실제 시작 날짜 (8자리)
     */
    @Column(name = "CCPY_RSLT_START_DT", length = 8)
    private String ccpyRsltStartDt;

    /**
     * 복사 결과 종료일
     * 복사 작업의 실제 종료 날짜 (8자리)
     */
    @Column(name = "CCPY_RSLT_END_DT", length = 8)
    private String ccpyRsltEndDt;

    /**
     * 복사 결과 지연일
     * 복사 작업의 지연된 일수 (4000자리)
     */
    @Column(name = "CCPY_RSLT_DLAY", length = 4000)
    private String ccpyRsltDlay;

    /**
     * 복사 결과 방법
     * 복사 작업에 사용된 방법 (4000자리)
     */
    @Column(name = "CCPY_RSLT_METHOD", length = 4000)
    private String ccpyRsltMethod;

    /**
     * 복사 결과 측정값
     * 복사 작업의 측정 결과 (4000자리)
     */
    @Column(name = "CCPY_RSLT_MEAS", length = 4000)
    private String ccpyRsltMeas;

    /**
     * 복사 결과 목표일
     * 복사 작업의 목표 날짜 (8자리)
     */
    @Column(name = "CCPY_RSLT_GOAL_DT", length = 8)
    private String ccpyRsltGoalDt;

    /**
     * 관리 변경 관리자
     * 관리 변경을 담당하는 관리자 (10자리)
     */
    @Column(name = "MNGT_CHRG_MAN", length = 10)
    private String mngtChrgMan;

    /**
     * 관리 계획 시작일
     * 관리 계획의 시작 날짜 (8자리)
     */
    @Column(name = "MNGT_PLAN_START_DT", length = 8)
    private String mngtPlanStartDt;

    /**
     * 관리 계획 종료일
     * 관리 계획의 종료 날짜 (8자리)
     */
    @Column(name = "MNGT_PLAN_END_DT", length = 8)
    private String mngtPlanEndDt;

    /**
     * 관리 계획 변경일
     * 관리 계획이 변경된 날짜 (8자리)
     */
    @Column(name = "MNGT_PLAN_CHG_DT", length = 8)
    private String mngtPlanChgDt;

    /**
     * 관리 결과 시작일
     * 관리 작업의 실제 시작 날짜 (8자리)
     */
    @Column(name = "MNGT_RSLT_START_DT", length = 8)
    private String mngtRsltStartDt;

    /**
     * 관리 결과 종료일
     * 관리 작업의 실제 종료 날짜 (8자리)
     */
    @Column(name = "MNGT_RSLT_END_DT", length = 8)
    private String mngtRsltEndDt;

    /**
     * 관리 반환일
     * 관리 작업의 반환 날짜 (8자리)
     */
    @Column(name = "MNGT_RETRN_DT", length = 8)
    private String mngtRetrnDt;

    /**
     * 관리 반환 카운트
     * 관리 작업의 반환 횟수 (3자리)
     */
    @Column(name = "MNGT_RETRN_CNT", precision = 3)
    private Integer mngtRetrnCnt;

    /**
     * 관리 반환 지연일
     * 관리 반환의 지연된 일수 (4000자리)
     */
    @Column(name = "MNGT_RETRN_DLAY", length = 4000)
    private String mngtRetrnDlay;

    /**
     * 관리 반환 방법
     * 관리 반환에 사용된 방법 (4000자리)
     */
    @Column(name = "MNGT_RETRN_METHOD", length = 4000)
    private String mngtRetrnMethod;

    /**
     * 관리 반환 측정값
     * 관리 반환의 측정 결과 (4000자리)
     */
    @Column(name = "MNGT_RETRN_MEAS", length = 4000)
    private String mngtRetrnMeas;

    /**
     * 관리 반환 목표일
     * 관리 반환의 목표 날짜 (8자리)
     */
    @Column(name = "MNGT_RETRN_GOAL_DT", length = 8)
    private String mngtRetrnGoalDt;

    /**
     * 사용자 변경 관리자 메인
     * 사용자 변경을 담당하는 메인 관리자 (10자리)
     */
    @Column(name = "CUST_CHRG_MAN_MAIN", length = 10)
    private String custChrgManMain;

    /**
     * 사용자 변경 관리자 서브
     * 사용자 변경을 담당하는 서브 관리자 (10자리)
     */
    @Column(name = "CUST_CHRG_MAN_SUB", length = 10)
    private String custChrgManSub;

    /**
     * 사용자 계획 시작일
     * 사용자 작업의 계획된 시작 날짜 (8자리)
     */
    @Column(name = "CUST_PLAN_START_DT", length = 8)
    private String custPlanStartDt;

    /**
     * 사용자 계획 종료일
     * 사용자 작업의 계획된 종료 날짜 (8자리)
     */
    @Column(name = "CUST_PLAN_END_DT", length = 8)
    private String custPlanEndDt;

    /**
     * 사용자 계획 변경일
     * 사용자 계획이 변경된 날짜 (8자리)
     */
    @Column(name = "CUST_PLAN_CHG_DT", length = 8)
    private String custPlanChgDt;

    /**
     * 사용자 결과 시작일
     * 사용자 작업의 실제 시작 날짜 (8자리)
     */
    @Column(name = "CUST_RSLT_START_DT", length = 8)
    private String custRsltStartDt;

    /**
     * 사용자 결과 종료일
     * 사용자 작업의 실제 종료 날짜 (8자리)
     */
    @Column(name = "CUST_RSLT_END_DT", length = 8)
    private String custRsltEndDt;

    /**
     * 사용자 반환일
     * 사용자 작업의 반환 날짜 (8자리)
     */
    @Column(name = "CUST_RETRN_DT", length = 8)
    private String custRetrnDt;

    /**
     * 사용자 반환 카운트
     * 사용자 작업의 반환 횟수 (3자리)
     */
    @Column(name = "CUST_RETRN_CNT", precision = 3)
    private Integer custRetrnCnt;

    /**
     * 사용자 반환 지연일
     * 사용자 반환의 지연된 일수 (4000자리)
     */
    @Column(name = "CUST_RETRN_DLAY", length = 4000)
    private String custRetrnDlay;

    /**
     * 사용자 반환 방법
     * 사용자 반환에 사용된 방법 (4000자리)
     */
    @Column(name = "CUST_RETRN_METHOD", length = 4000)
    private String custRetrnMethod;

    /**
     * 사용자 반환 측정값
     * 사용자 반환의 측정 결과 (4000자리)
     */
    @Column(name = "CUST_RETRN_MEAS", length = 4000)
    private String custRetrnMeas;

    /**
     * 사용자 반환 목표일
     * 사용자 반환의 목표 날짜 (8자리)
     */
    @Column(name = "CUST_RETRN_GOAL_DT", length = 8)
    private String custRetrnGoalDt;

    /**
     * 출력명
     * 출력되는 결과물의 이름 (500자리)
     */
    @Column(name = "OUTPUT_NM", length = 500)
    private String outputNm;

    /**
     * 비고
     * 추가 설명이나 특이사항 (4000자리)
     */
    @Column(name = "RMRK", length = 4000)
    private String rmrk;

    /**
     * 수정 개발자
     * 수정 작업을 담당한 개발자 (5자리)
     */
    @Column(name = "MODI_DEV", length = 5)
    private String modiDev;

    /**
     * 수정 내용
     * 수정된 내용에 대한 설명 (4000자리)
     */
    @Column(name = "MODI_CNTN", length = 4000)
    private String modiCntn;

    /**
     * 등록자 ID
     * 데이터를 등록한 사용자의 ID (20자리)
     */
    @Column(name = "REGI_ID", length = 20)
    private String regiId;

    /**
     * 등록일시
     * 데이터가 등록된 일시
     * @CreatedDate JPA Auditing으로 자동 설정
     */
    @CreatedDate
    @Column(name = "REGI_DT")
    private Date regiDt;

    /**
     * 수정자 ID
     * 데이터를 수정한 사용자의 ID (20자리)
     */
    @Column(name = "UPDT_ID", length = 20)
    private String updtId;

    /**
     * 수정일시
     * 데이터가 수정된 일시
     * @LastModifiedDate JPA Auditing으로 자동 설정
     */
    @LastModifiedDate
    @Column(name = "UPDT_DT")
    private Date updtDt;
} 