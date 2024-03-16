package com.legend.common.patterns.create.build;

/**
 * 建造者模式-客户端
 *
 * @author xlj
 * @date 2020/12/16 23:21
 */
public class BuilderClient {
    public static void main(String[] args) {
        CommonHouse commonHouse = new CommonHouse();

        HouseDirect houseDirect = new HouseDirect(commonHouse);

        House house = houseDirect.createHouse();
    }
}
