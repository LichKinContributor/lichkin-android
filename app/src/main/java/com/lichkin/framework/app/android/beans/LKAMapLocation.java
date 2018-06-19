package com.lichkin.framework.app.android.beans;

import com.amap.api.location.AMapLocation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 定位信息
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LKAMapLocation {

    /** 当前定位结果来源，如网络定位结果，详见定位类型表 */
    private int locationType;

    /** 纬度 */
    private double latitude;

    /** 经度 */
    private double longitude;

    /** 精度信息 */
    private float accuracy;

    /** 地址 */
    private String address;

    /** 国家信息 */
    private String country;

    /** 省信息 */
    private String province;

    /** 城市信息 */
    private String city;

    /** 城区信息 */
    private String district;

    /** 街道信息 */
    private String street;

    /** 街道门牌号信息 */
    private String streetNum;

    /** 城市编码 */
    private String cityCode;

    /** 地区编码 */
    private String adCode;

    /** 当前定位点的AOI信息 */
    private String aoiName;

    /** 当前室内定位的建筑物Id */
    private String buildingId;

    /** 当前室内定位的楼层 */
    private String floor;

    /** GPS的当前状态 */
    private int gpsAccuracyStatus;

    /** 定位时间 */
    private long time;

    /** 方向角 */
    private float bearing;

    /** 高度 */
    private double altitude;

    /** 描述 */
    private String description;

    /**
     * 构造方法
     * @param location 定位信息
     */
    public LKAMapLocation(AMapLocation location) {
        locationType = location.getLocationType();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        accuracy = location.getAccuracy();
        address = location.getAddress();
        country = location.getCountry();
        province = location.getProvince();
        city = location.getCity();
        district = location.getDistrict();
        street = location.getStreet();
        streetNum = location.getStreetNum();
        cityCode = location.getCityCode();
        adCode = location.getAdCode();
        aoiName = location.getAoiName();
        buildingId = location.getBuildingId();
        floor = location.getFloor();
        gpsAccuracyStatus = location.getGpsAccuracyStatus();
        time = location.getTime();
        bearing = location.getBearing();
        altitude = location.getAltitude();
        description = location.getDescription();
    }

}
