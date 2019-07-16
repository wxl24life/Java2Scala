package com.ddu.demo.java.filterchain;

/**
 * filter chain desgin pattern demo.
 *
 * This is a "hello world" version of filterChain implementation in
 * <a href="https://github.com/alibaba/druid">alibaba/druid</a>
 *
 * @author wxl24life
 */
public class Main {
    public static void main(String[] args) {
        FilterChainImpl filterChain = new FilterChainImpl();
        filterChain.addFilter(new LogFilter());
        filterChain.addFilter(new StatFilter());

        filterChain.dataSource_connect();
        // chain, state, log
    }
}
