package com.ddu.demo.java.desginpattern.singleton;

/**
 * 线程不安全的实现版本
 *
 * @author wxl24life
 */
public class SingletonVersionUnThreadSafe {

    private static SingletonVersionUnThreadSafe instance;

    private SingletonVersionUnThreadSafe() {}

    public static SingletonVersionUnThreadSafe getInstance() {
        if (instance == null) {
            instance = new SingletonVersionUnThreadSafe();
        }
        return instance;
    }
}
