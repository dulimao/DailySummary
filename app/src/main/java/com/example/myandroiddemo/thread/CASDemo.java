package com.example.myandroiddemo.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {

    private AtomicInteger atomicInteger = new AtomicInteger(0);


    private int getCount() {
        atomicInteger.incrementAndGet()
        return atomicInteger.get();
    }

    private void increment() {
        int oldCount;
        int newCount;
        do {
            oldCount = getCount();
            newCount = oldCount + 1;
        } while (!atomicInteger.compareAndSet(oldCount,newCount));
    }


    public static void main(String[] args) {
        CASDemo casDemo = new CASDemo();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    casDemo.increment();
                }
            });
            threads[i].start();
        }
        System.out.println("线程全部启动");

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("count: " + casDemo.getCount());
    }
}
