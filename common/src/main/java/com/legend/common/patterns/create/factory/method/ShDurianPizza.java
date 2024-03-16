package com.legend.common.patterns.create.factory.method;


import com.legend.common.patterns.create.factory.simple.Pizza;

/**
 * @author xlj
 * @date 2020/12/15 21:41
 */
public class ShDurianPizza extends Pizza {
    @Override
    public void prepare() {
        this.setName("上海榴莲披萨");
        System.out.println("上海榴莲披萨准备原材料");
    }
}
