package com.legend.common.patterns.behavior.observer;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public interface WeatherObserver {
    void dataUpdate(int temperature, int humidity);
    void display();
}
