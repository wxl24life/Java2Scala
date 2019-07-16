package com.ddu.demo.java.filterchain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wxl24life
 */
public class FilterChainImpl implements FilterChain {
    private List<Filter> filters = new CopyOnWriteArrayList<>();
    private int pos = 0;
    private int size = 0;

    FilterChainImpl() {}

    void addFilter(Filter filter) {
        this.filters.add(filter);
        this.size++;
    }

    private Filter nextFilter() {
        return filters.get(pos++);
    }

    @Override
    public void dataSource_connect() {
        if (pos < size) {
            nextFilter().dataSource_getConnection(this);
            return;
        }

        System.out.println("chain's dataSource_connnect called");
    }
}
