package com.ddu.demo.java.filterchain;

/**
 * @author wxl24life
 */
public interface Filter {
    void dataSource_getConnection(FilterChain chain);
}
