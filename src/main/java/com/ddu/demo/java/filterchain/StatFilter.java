package com.ddu.demo.java.filterchain;

/**
 * @author wxl24life
 */
public class StatFilter implements Filter {
    @Override
    public void dataSource_getConnection(FilterChain chain) {
        chain.dataSource_connect();

        System.out.println("stat filter called");
    }
}
