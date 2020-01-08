package com.ddu.demo.java.desginpattern.singleton;

/**
 * @author wxl24life
 */
public class SingletonDriver {

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.ddu.demo.java.desginpattern.singleton.SingletonVersion1");

        // SingletonVersion1对象创建初始化，控制台会打印 SingletonVersion1 initiated

        SingletonVersionEnumClass single5 = SingletonVersionEnumClass.SINGLE;
        single5.print();

        // SingletonVersion5 printed
    }
}
