package com.legend.common.patterns.behavior.template;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public abstract class Game {
    abstract void initialize();

    abstract void startPlay();

    abstract void endPlay();

    // 模板方法
    public final void play() {
        if (reading()) {
            // 初始化游戏
            initialize();
        }
        //开始游戏
        startPlay();
        //结束游戏
        endPlay();
    }

    // 钩子方法
    public boolean reading() {
        return false;
    }
}