package com.ddu.demo.java.desginpattern.singleton;

/**
 * 静态内部类实现（推荐）
 *
 * 优势：兼顾了懒汉模式的内存优化（使用时才初始化）以及饿汉模式的安全性（不会被反射入侵）。
 * 劣势：需要两个类去做到这一点，虽然不会创建静态内部类的对象，但是其 Class 对象还是会被创建，而且是属于永久带的对象。
 *
 * @author wxl24life
 */
public class SingletonVersionStaticInnerClass {

    private SingletonVersionStaticInnerClass() {}

    public static SingletonVersionStaticInnerClass getInstance() {
        return SingletonVersion6Holder.singleton;
    }

    private static class SingletonVersion6Holder {
        private static final SingletonVersionStaticInnerClass singleton = new SingletonVersionStaticInnerClass();
    }
}
