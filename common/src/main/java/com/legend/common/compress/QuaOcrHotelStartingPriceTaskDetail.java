package com.legend.common.compress;


import lombok.Data;

@Data
public class QuaOcrHotelStartingPriceTaskDetail {
    public static final String COUNTRY_PATH = "country";
    public static final String PROVINCE_PATH = "province";
    public static final String CITY_PATH = "city";
    public static final String AREA_PATH = "area";

    // 是否按城市等build
    private boolean needBuildByCity;
    private String buildPath;
    private String province;
    private String cityId;
    private String cityName;
    private String hotelId;
    private String hotelName;
    private String checkInDate;
    private String checkOutDate;
    private String hotelAddress;
    private String shareShortLink;
    private String sharePassword;
    public String type = QuaOcrHotelStartingPriceTaskDetail.class.getSimpleName();
    public String resultType = "QuaOcrHotelStartingPriceResultDetail";
    private int isundercarriage = -1;// 是否下架，是1，否0
    private int canBooking = -1;// 是否可订，是1，否0，判断暂无报价
    private int isLinkInvalidate = -1;// 短链接是否失效，是1，否0
    private String hotelStatusDesc;// 可订状态描述
    private String laterPay;// 信用住
}
