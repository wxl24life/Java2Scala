package com.ddu.demo.java.builder;

/**
 *
 * Builder Pattern demo
 *
 * @author wxl24life
 */
public class SomeConfig<K, V> {
    private K foo;
    private V bar;

    public SomeConfig(Builder<K, V> builder) {
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

        public SomeConfig<K, V> build() {
            return new SomeConfig<K, V>(this);
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

    public static void main(String[] args) {
        Builder<String, String> builder = SomeConfig.builder("fooooo", "barrrr");
        SomeConfig<String, String> stringSomeConfig = new SomeConfig<>(builder);
        System.out.println(stringSomeConfig.getFoo() + "|" + stringSomeConfig.getBar());

        Builder<byte[], byte[]> byteArrayConfigBuilder = new Builder<>();
        byteArrayConfigBuilder.setFoo("foo".getBytes()).setBar("bar".getBytes());
    }

}
