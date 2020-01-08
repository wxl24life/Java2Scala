package com.ddu.demo.java.desginpattern.singleton;

/**
 *
 * 懒汉式实现，线程安全：双重检验锁
 *
 * 缺点是：序列化和反射模式下会创建额外的对象
 *
 * @author wxl24life
 */
public class SingletonVersionDoubleCheckingLock {

    // volatile 修饰符保证变量的内存可见性（防止指令重排序，可能在 instance 位初始化完成前，另一个线程返回了 null）
    // https://juejin.im/post/5cf09270f265da1bd260d239
    private static volatile SingletonVersionDoubleCheckingLock instance;

    private SingletonVersionDoubleCheckingLock() {}

    public static SingletonVersionDoubleCheckingLock getInstance() {
        if (instance == null) {
            // 只有instance未初始化才会做加锁处理
            synchronized (SingletonVersionDoubleCheckingLock.class) {
                if (instance == null) {
                    instance = new SingletonVersionDoubleCheckingLock();
                }
            }
        }

        return instance;
    }
}
