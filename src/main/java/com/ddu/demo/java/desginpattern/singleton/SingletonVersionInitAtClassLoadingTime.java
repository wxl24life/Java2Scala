package com.ddu.demo.java.desginpattern.singleton;

/**
 * @author wxl24life
 */
public class SingletonVersionInitAtClassLoadingTime {

    // 最简单版本的实现，私有类成员变量，能够保证单例，线程安全
    // 没有用懒加载（此处叫"饿汉式"），在类加载阶段就会创建 instance 对象
    private static final SingletonVersionInitAtClassLoadingTime instance = new SingletonVersionInitAtClassLoadingTime();

    private SingletonVersionInitAtClassLoadingTime() {
        System.out.println("SingletonVersion1 initiated");
    }

    public static SingletonVersionInitAtClassLoadingTime getInstance() {
        return instance;
    }
}
