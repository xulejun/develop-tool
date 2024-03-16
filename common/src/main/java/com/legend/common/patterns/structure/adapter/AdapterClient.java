package com.legend.common.patterns.structure.adapter;

/**
 * 结构型模式：适配器模式
 *  场景：原先只有苹果手机和安卓手机，都只适用一个 5v接口，新增电脑，同样也需要使用该接口，但它是属于220v的，无法连接
 *
 * @author legend xu
 * @date 2024/3/16
 */
public class AdapterClient {
    public static void main(String[] args) {
        IVoltage5V androidPhone = new AndroidPhone();
        androidPhone.output5v();
        IPhone iPhone = new IPhone();
        iPhone.output5v();

        // 使用适配器实现
        VoltageAdapter adapter = new VoltageAdapter(new Computer());
        adapter.output5v();
    }
}
