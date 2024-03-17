package com.legend.common.patterns.behavior.template;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class CFGame extends Game{
    @Override
    void initialize() {
        System.out.println("CF 游戏初始化");
    }

    @Override
    void startPlay() {
        System.out.println("CF 开始");
    }

    @Override
    void endPlay() {
        System.out.println("CF 游戏结束");
    }
}
