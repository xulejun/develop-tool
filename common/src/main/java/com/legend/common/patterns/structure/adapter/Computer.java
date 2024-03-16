package com.legend.common.patterns.structure.adapter;

/**
 * @author legend xu
 * @date 2024/3/16
 */
public class Computer implements IVoltage220V{
    @Override
    public int output220v() {
        return 220;
    }
}
