package com.ddu.demo.java.desginpattern.singleton;

/**
 * 饿汉式实现：枚举类
 *
 * 1. 线程安全(利用类初始化锁)
 * 2. 枚举单例模式能够在序列化和反射中保证实例的唯一性。
 * @author wxl24life
 */
public enum SingletonVersionEnumClass {

    SINGLE;

    private SingletonVersionEnumClass() {}

    public void print() {
        System.out.println("SingletonVersion5 printed");
    }

}
