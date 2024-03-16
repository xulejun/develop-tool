package com.legend.common.patterns.structure.proxy;

/**
 * @author legend xu
 * @date 2024/3/16
 */
public class MathTeacher implements ITeacher{
    @Override
    public void teacher() {
        System.out.println("数学老师授课中");
    }

    @Override
    public String name() {
        return "数学老师-张三";
    }
}
