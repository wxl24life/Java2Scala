package com.ddu.demo.java.desginpattern.proxy;

/**
 * @author wxl24life
 */
public class StaticProxyMain {
    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        ICalculator calculatorProxy = new CalculatorProxy(calculator);
        calculator.sum(1, 2);
        calculatorProxy.sum(3, 4);
    }
}
