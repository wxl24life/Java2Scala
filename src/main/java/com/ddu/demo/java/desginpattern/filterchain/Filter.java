package com.ddu.demo.java.desginpattern.filterchain;

/**
 * @author wxl24life
 */
public interface Filter {
    void dataSource_getConnection(FilterChain chain);
}
