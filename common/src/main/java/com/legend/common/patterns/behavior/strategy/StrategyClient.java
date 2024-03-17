package com.legend.common.patterns.behavior.strategy;

/**
 * 行为型模式：策略模式
 *  它使你能在运行时改变对象的行为。在策略模式中，一个类的行为或其算法可以在其运行时更改。这种类型的设计模式属于行为模式
 *  通常与工厂模式相结合使用
 *
 * @author legend xu
 * @date 2024/3/17
 */
public class StrategyClient {
    public static void main(String[] args) {
        StrategyContext context = new StrategyContext(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new StrategyContext(new OperationSubtract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new StrategyContext(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }
}
