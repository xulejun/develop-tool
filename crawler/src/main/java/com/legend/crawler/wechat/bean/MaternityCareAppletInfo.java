package com.legend.crawler.wechat.bean;


import lombok.Data;

/**
 * 江西妇保小程序九价详细信息
 *
 * @author legend xu
 * @String 2021/11/29
 */
@Data
public class MaternityCareAppletInfo {
    private String deptName;
    private String goodAt;
    private String ampm;
    private String docId;
    private String fee;
    private String ampmName;
    private String docTitle;
    private String docJob;
    /**
     * 预约时间
     */
    private String schDate;
    private String schId;
    /**
     * 预约场次名称
     */
    private String docName;
    private String categorName;
    /**
     * 放号总数量
     */
    private String numHadReg;
    private String regFee;
    private String canCivilServantInsurance;
    private String intro;
    /**
     * 剩余数量
     */
    private String numRemain;
    private String hosName;
    private String canInsurance;
    private String docJobCode;
    private String deptId;
    private String hosId;
    /**
     * 预约状态
     */
    private String schStateName;
    private String extend;
    private String queryDocImage;
    private String showNo;
    private String schState;
    private String numCount;
    private String categor;
    private String docTitleName;

}
