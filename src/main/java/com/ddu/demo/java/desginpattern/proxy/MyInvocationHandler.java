package com.ddu.demo.java.desginpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wxl24life
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object targetObject;

    public Object newProxyInstance(Object targetObject) {
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke method..");
        Object invoke = method.invoke(targetObject, args);
        System.out.println("After invoke method..");
        return invoke;
    }

    public static void main(String[] args) {
        MyInvocationHandler invocationHandler = new MyInvocationHandler();
        ICalculator calculatorProxy = (ICalculator) invocationHandler.newProxyInstance(new CalculatorImpl());
        calculatorProxy.sum(3, 4);
    }
}
