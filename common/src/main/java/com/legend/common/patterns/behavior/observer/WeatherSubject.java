package com.legend.common.patterns.behavior.observer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class WeatherSubject {
    // 温度
    private int temperature;
    // 湿度
    private int humidity;

    private List<WeatherObserver> observers = Lists.newArrayList();

    public void update(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        notifyObserver();
    }

    public void setObservers(WeatherObserver observer) {
        observers.add(observer);
    }

    public void notifyObserver() {
        for (WeatherObserver observer : observers) {
            observer.dataUpdate(temperature, humidity);
        }
    }
}
