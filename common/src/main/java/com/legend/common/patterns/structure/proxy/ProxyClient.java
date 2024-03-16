package com.legend.common.patterns.structure.proxy;

/**
 * @author legend xu
 * @date 2024/3/16
 */
public class ProxyClient {
    public static void main(String[] args) {
        ITeacher teacher = new MathTeacher();
        ITeacher mathTeacher = (ITeacher)new ProxyFactory(teacher).getProxyInstance();
        mathTeacher.teacher();
        String name = mathTeacher.name();
        System.out.println(name);
    }
}
