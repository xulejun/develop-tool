package com.legend.common.patterns.behavior.observer;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class BaiduWeather implements WeatherObserver {
    // 温度
    private int temperature;
    // 湿度
    private int humidity;

    @Override
    public void dataUpdate(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        System.out.println("百度天气的温度：" + temperature);
        System.out.println("百度天气的湿度：" + humidity);
    }
}
