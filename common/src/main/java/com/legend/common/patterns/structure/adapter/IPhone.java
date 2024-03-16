package com.legend.common.patterns.structure.adapter;

/**
 * @author legend xu
 * @date 2024/3/16
 */
public class IPhone implements IVoltage5V{
    @Override
    public int output5v() {
        System.out.println("苹果手机 5V 电压充电");
        return 5;
    }
}
