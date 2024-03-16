package com.legend.common.patterns.structure.adapter;

/**
 * @author legend xu
 * @date 2024/3/16
 */
public class VoltageAdapter implements IVoltage5V{
    private final IVoltage220V IVoltage220V;

    public VoltageAdapter(IVoltage220V IVoltage220V) {
        this.IVoltage220V = IVoltage220V;
    }

    @Override
    public int output5v() {
        int src = IVoltage220V.output220v();
        int dst = src / 44;
        System.out.println("通过适配器实现充电");
        return dst;
    }
}
