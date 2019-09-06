package com.ddu.demo.java.desginpattern.filterchain;

/**
 * @author wxl24life
 */
public class LogFilter implements Filter{
    @Override
    public void dataSource_getConnection(FilterChain chain) {
        System.out.println("log filter called");
        chain.dataSource_connect();
    }
}
