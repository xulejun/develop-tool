package com.legend.common.patterns.behavior.strategy;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class StrategyContext {
    private Strategy strategy;

    public StrategyContext(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}
