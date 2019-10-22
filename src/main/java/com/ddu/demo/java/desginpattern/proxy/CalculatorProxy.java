package com.ddu.demo.java.desginpattern.proxy;

/**
 * @author wxl24life
 */
public class CalculatorProxy implements ICalculator {
    private ICalculator calculator;

    public CalculatorProxy(ICalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public int sum(int a, int b) {
        System.out.println(String.format("To sum %d and %d", a, b));
        return calculator.sum(a, b);
    }
}
