package com.legend.common.compress;

import lombok.Data;

/**
 * @author lejunxu
 */
@Data
public class Task {
    private int type;
    private long buildTime;
    private int id;
    private int priority;
    private QuaOcrHotelStartingPriceTaskDetail taskDetail;
    private String proxy="proxy";
    private String level = "P0";
}
