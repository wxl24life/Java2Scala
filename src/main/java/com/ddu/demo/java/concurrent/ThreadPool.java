package com.ddu.demo.java.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author wxl24life
 */
public class ThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread() + " | " + new Random().nextInt());
                }
            });
        }
        TimeUnit.SECONDS.sleep(100);

        Collections.synchronizedMap(new HashMap<>());
    }
}
