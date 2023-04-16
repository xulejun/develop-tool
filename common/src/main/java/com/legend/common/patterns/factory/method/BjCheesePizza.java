package com.legend.common.patterns.factory.method;


import com.legend.common.patterns.factory.simple.Pizza;

/**
 * @author xlj
 * @date 2020/12/15 21:41
 */
public class BjCheesePizza extends Pizza {
    @Override
    public void prepare() {
        this.setName("北京奶酪披萨");
        System.out.println("北京奶酪披萨准备原材料");
    }
}
