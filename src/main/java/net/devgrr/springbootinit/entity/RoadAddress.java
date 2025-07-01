package net.devgrr.springbootinit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 도로 주소 엔티티
 * 
 * 도로명 주소 시스템의 주소 정보를 관리하는 엔티티입니다.
 * 건물의 도로명 주소와 관련된 상세 정보를 저장합니다.
 * 
 * @author devgrr
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "CM_ROAD_ADDR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadAddress {

    /**
     * 도로명 코드
     * 도로의 고유 식별 코드입니다.
     */
    @Id
    @Column(name = "RN_CD", length = 20)
    private String roadNameCode;

    /**
     * 읍면동 일련번호
     * 읍면동의 일련번호입니다.
     */
    @Column(name = "EMD_SN", length = 3)
    private String emdSerialNumber;

    /**
     * 지하 여부
     * 해당 주소가 지하에 위치하는지 여부입니다.
     */
    @Column(name = "UNDGRND_YN", length = 1)
    private String undergroundYn;

    /**
     * 건물 본번
     * 건물의 본번호입니다.
     */
    @Column(name = "BDNBR_MNNM", length = 4)
    private String buildingMainNumber;

    /**
     * 건물 부번
     * 건물의 부번호입니다.
     */
    @Column(name = "BDNBR_SLNO", length = 4)
    private String buildingSubNumber;

    /**
     * 건물 관리번호
     * 건물의 관리를 위한 고유 번호입니다.
     */
    @Column(name = "BULD_MNG_NO", length = 30)
    private String buildingManagementNumber;

    /**
     * 우편번호
     * 해당 주소의 우편번호입니다.
     */
    @Column(name = "ZIP_CD", length = 6)
    private String zipCode;

    /**
     * 시도 일련번호
     * 시도의 일련번호입니다.
     */
    @Column(name = "POST_SN", length = 3)
    private String postSerialNumber;

    /**
     * 시군구 한글명
     * 시군구의 한글 명칭입니다.
     */
    @Column(name = "CTPRVN", length = 50)
    private String cityProvince;

    /**
     * 읍면동 한글명
     * 읍면동의 한글 명칭입니다.
     */
    @Column(name = "SIGNGU", length = 50)
    private String siGunGu;

    /**
     * 읍면동 영문명
     * 읍면동의 영문 명칭입니다.
     */
    @Column(name = "SIGNGU_ENG", length = 50)
    private String siGunGuEng;

    /**
     * 읍면동명
     * 읍면동의 명칭입니다.
     */
    @Column(name = "EMD", length = 50)
    private String emd;

    /**
     * 읍면동 영문명
     * 읍면동의 영문 명칭입니다.
     */
    @Column(name = "EMD_ENG", length = 50)
    private String emdEng;

    /**
     * 도로명
     * 도로의 한글 명칭입니다.
     */
    @Column(name = "RN", length = 50)
    private String roadName;

    /**
     * 도로명 영문명
     * 도로의 영문 명칭입니다.
     */
    @Column(name = "RN_ENG", length = 50)
    private String roadNameEng;

    /**
     * 건물명
     * 건물의 명칭입니다.
     */
    @Column(name = "LNGT", length = 100)
    private String buildingName;

    /**
     * 시군구 건물명
     * 시군구 단위의 건물명입니다.
     */
    @Column(name = "SIGNGU_BLDG_NM", length = 1)
    private String siGunGuBuildingName;

    /**
     * 법정동 코드
     * 법정동의 코드입니다.
     */
    @Column(name = "LEGALDONG_CD", length = 10)
    private String legalDongCode;

    /**
     * 법정동명
     * 법정동의 명칭입니다.
     */
    @Column(name = "LEGALDONG_NM", length = 50)
    private String legalDongName;

    /**
     * 리명
     * 리의 명칭입니다.
     */
    @Column(name = "LI", length = 50)
    private String li;

    /**
     * 산 여부
     * 해당 주소가 산에 위치하는지 여부입니다.
     */
    @Column(name = "MNTN_YN", length = 1)
    private String mountainYn;

    /**
     * 지번 본번
     * 지번의 본번호입니다.
     */
    @Column(name = "LNM_MNNM", length = 4)
    private String landNumberMainNumber;
} 