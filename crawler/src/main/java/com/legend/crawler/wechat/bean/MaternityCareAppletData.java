package com.legend.crawler.wechat.bean;

import lombok.Data;

import java.util.List;

/**
 * 当前日期所含有的预约场次信息
 *
 * @author legend xu
 * @String 2021/11/29
 */
@Data
public class MaternityCareAppletData {
    private String schDate;
    private String categorName;
    private List<MaternityCareAppletInfo> schemeList;
    private String categorCode;
}
