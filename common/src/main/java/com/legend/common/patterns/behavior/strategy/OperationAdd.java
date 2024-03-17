package com.legend.common.patterns.behavior.strategy;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class OperationAdd implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}