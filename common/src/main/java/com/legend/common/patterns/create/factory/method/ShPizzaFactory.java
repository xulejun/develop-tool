package com.legend.common.patterns.create.factory.method;


import com.legend.common.patterns.create.factory.simple.Pizza;

/**
 * 工厂方法模式：上海披萨
 *
 * @author xlj
 * @date 2020/12/15 21:59
 */
public class ShPizzaFactory extends OrderPizza {
    private static final String CHEESE = "cheese";
    private static final String DURIAN = "durian";

    @Override
    public Pizza createPizza(String pizzaType) {
        Pizza pizza = null;
        if (CHEESE.equals(pizzaType)) {
            pizza = new ShCheesePizza();
        }
        if (DURIAN.equals(pizzaType)) {
            pizza = new ShDurianPizza();
        }
        return pizza;
    }
}
