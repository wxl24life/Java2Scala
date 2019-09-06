package com.ddu.demo.java.desginpattern.builder;

/**
 *
 * A class definition with "GOF" builder design pattern.
 * The whole structure is a simulation of that in KafkaSpoutConfig from storm-kafka client jar.
 *
 * @author wxl24life
 */
public class SomeConfigWithBuilder<K, V> {
    private K foo;
    private V bar;

    public SomeConfigWithBuilder(Builder<K, V> builder) {
        this.foo = builder.foo;
        this.bar = builder.bar;
    }

    public static class Builder<K, V> {
        private K foo;
        private V bar;

        public Builder() {}

        public Builder<K, V> setFoo(K foo) {
            this.foo = foo;
            return this;
        }

        public Builder<K, V> setBar(V bar) {
            this.bar = bar;
            return this;
        }

        public SomeConfigWithBuilder<K, V> build() {
            return new SomeConfigWithBuilder<K, V>(this);
        }
    }

    // factory method to create String Builder
    public static Builder<String, String> builder(String foo, String bar) {
        return new Builder<String, String>().setFoo(foo).setBar(bar);
    }

    public K getFoo() {
        return foo;
    }

    public V getBar() {
        return bar;
    }

    @Override
    public String toString() {
        return foo + "|" + bar;
    }

    public static void main(String[] args) {
        Builder<String, String> builder = SomeConfigWithBuilder.builder("fooooo", "barrrr");
        SomeConfigWithBuilder<String, String> stringConfig = new SomeConfigWithBuilder<>(builder);
        System.out.println(stringConfig);

        stringConfig.foo = "foooo";
        System.out.println(stringConfig);

        Builder<byte[], byte[]> byteArrayConfigBuilder = new Builder<>();
        byteArrayConfigBuilder.setFoo("foo".getBytes()).setBar("bar".getBytes());
        SomeConfigWithBuilder<byte[], byte[]> byteArrayConfig = new SomeConfigWithBuilder<>(byteArrayConfigBuilder);
        System.out.println(byteArrayConfig);
    }

}
