package com.legend.common.patterns.create.factory.method;


import com.legend.common.patterns.create.factory.simple.Pizza;

/**
 * @author xlj
 * @date 2020/12/15 21:41
 */
public class BjDurianPizza extends Pizza {
    @Override
    public void prepare() {
        this.setName("北京榴莲披萨");
        System.out.println("北京榴莲披萨准备原材料");
    }
}
