package com.ddu.demo.java.desginpattern.singleton;

/**
 * 使用 synchronized 保证线程安全，但是同步代价大，高并发场景下性能差
 * @author wxl24life
 */
public class SingletonVersionSynchronized {

    private static SingletonVersionSynchronized instance;

    private SingletonVersionSynchronized() {}

    public static SingletonVersionSynchronized getInstance() {
        // 每次调用 getInstance 都会加锁检查, synchronized 关键字放在方法签名处效果是类似的
        synchronized(SingletonVersionSynchronized.class) {
            if (instance == null) {
                instance = new SingletonVersionSynchronized();
            }
        }
        return instance;
    }

    // synchronized 修饰整个方法
    public static synchronized SingletonVersionSynchronized getInstance2() {
        if (instance == null) {
            instance = new SingletonVersionSynchronized();
        }
        return instance;
    }
}
