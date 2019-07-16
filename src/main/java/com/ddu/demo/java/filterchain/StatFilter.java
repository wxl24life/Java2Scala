package com.ddu.demo.java.filterchain;

/**
 * @author wxl24life
 */
public class StatFilter implements Filter {
    @Override
    public void dataSource_getConnection(FilterChain chain) {
        System.out.println("stat filter called");
        chain.dataSource_connect();
    }
}
