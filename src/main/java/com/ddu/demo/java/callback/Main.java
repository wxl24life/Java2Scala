package com.ddu.demo.java.callback;

/**
 *
 * java callback implemention demo
 *
 * @author wxl24life
 */
public class Main {
    public static void main(String[] args) {
        doSomething(new IMyCallBack() {
            @Override
            public void callSomething() {
                System.out.println("first callback..");
            }
        });

        //
        IMyCallBack iMyCallBack = new IMyCallBack() {

            @Override
            public void callSomething() {
                System.out.println("another callback..");
            }
        };
        doSomething(iMyCallBack);

        //
        class MyCallBackImpl implements IMyCallBack {

            @Override
            public void callSomething() {
                System.out.println("one more callback..");
            }
        }
        doSomething(new MyCallBackImpl());
    }

    private static void doSomething(IMyCallBack cb) {
        // ..do some heavy operations
        cb.callSomething();
        // ..
        // ..do more heavy operations
    }
}
