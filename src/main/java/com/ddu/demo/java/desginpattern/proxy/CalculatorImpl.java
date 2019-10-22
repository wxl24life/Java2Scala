package com.ddu.demo.java.desginpattern.proxy;

/**
 * @author wxl24life
 */
public class CalculatorImpl implements ICalculator {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }
}
