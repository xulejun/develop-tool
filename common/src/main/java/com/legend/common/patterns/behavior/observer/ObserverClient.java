package com.legend.common.patterns.behavior.observer;

/**
 * @author legend xu
 * @date 2024/3/17
 */
public class ObserverClient {
    public static void main(String[] args) {
        // 注册观察者
        WeatherSubject weatherSubject = new WeatherSubject();
        weatherSubject.setObservers(new TencentWeather());
        weatherSubject.setObservers(new BaiduWeather());

        // 更新之后，直接通知
        weatherSubject.update(24, 14);

        weatherSubject.update(16,10);
    }
}
