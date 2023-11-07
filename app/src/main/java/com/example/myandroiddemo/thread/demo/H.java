package com.example.myandroiddemo.thread.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
//tryLock 解决死锁
public class H {

    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    private void product() {
        boolean acquired1 = lock1.tryLock();
        System.out.println(Thread.currentThread().getName() + " 持有了锁1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            boolean acquired2 = lock2.tryLock(5, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + " 持有了锁2");
        if (acquired1) {
            lock1.unlock();
        }
        if (acquired2) {
            lock2.unlock();
        }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void consume() {
        try {
            boolean acquire1 = lock2.tryLock();
            if (acquire1) {
                System.out.println(Thread.currentThread().getName() + " 持有了锁2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                boolean acquire2 = lock1.tryLock();
                if (acquire2) {
                    System.out.println(Thread.currentThread().getName() + " 持有了锁1");
                }
            }
        } catch (Exception e) {

        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }


    public static void main(String[] args) {
        H g = new H();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                g.product();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                g.consume();
            }
        });

        t1.start();
        t2.start();


    }
}
