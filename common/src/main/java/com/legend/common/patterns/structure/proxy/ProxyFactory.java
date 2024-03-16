package com.legend.common.patterns.structure.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理模式：
 *  代理对象，不需要实现接口，但是目标对象要实现接口，代理对象的生成是利用JDK的API，动态在内存中构建代理对象
 *  可获取多个实现不同接口的对象并调用对应的方法
 *
 * @author legend xu
 * @date 2024/3/16
 */
public class ProxyFactory {
    // 目标对象
    private Object target;

    // 通过聚合的方式传入目标对象
    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance(){
        // 1、ClassLoader loader：指定目标对象使用的类加载器
        // 2、Class<?>[] interfaces：目标对象实现的接口类型
        // 3、InvocationHandler h：事情处理，执行目标对象的方法时，会触发事情处理器方法，会把当前执行的目标对象方法作为参数传入
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("JDK 动态代理执行");
                return method.invoke(target, args);
            }
        });
    }
}
