package com.legend.common.patterns.behavior.template;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class GTAGame extends Game{
    @Override
    void initialize() {
        System.out.println("GTA 游戏初始化");
    }

    @Override
    void startPlay() {
        System.out.println("GTA 开始");
    }

    @Override
    void endPlay() {
        System.out.println("GTA 游戏结束");
    }

    @Override
    public boolean reading() {
        // 单机游戏需要读档，初始化
        return true;
    }
}
