package com.ddu.demo.java.builder;

/**
 * A simple class definition without builder
 *
 * @author wxl24life
 */
public class SomeConfigWithoutBuilder<K, V> {
    private K foo;
    private V bar;

    public K getFoo() {
        return foo;
    }

    public void setFoo(K foo) {
        this.foo = foo;
    }

    public V getBar() {
        return bar;
    }

    public void setBar(V bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return foo + "|" + bar;
    }

    public static void main(String[] args) {
        SomeConfigWithoutBuilder<String, String> stringConfig = new SomeConfigWithoutBuilder<>();
        stringConfig.setFoo("foo");
        stringConfig.setBar("bar");
        System.out.println(stringConfig);

        stringConfig.setFoo("foooo");
        System.out.println(stringConfig);
    }
}
