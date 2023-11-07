package com.example.myandroiddemo.thread.demo;

import java.nio.file.Watchable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class B {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean flag = false;
    private void waitThread() throws InterruptedException {
        lock.lock();
        try {
            if (!flag) {
                System.out.println("等待线程 进入等待。。。");
                Thread.sleep(1000);
                condition.await();
            }
            System.out.println("等待线程醒了 flag: " + flag);
        } finally {
            lock.unlock();
        }

    }

    private void notifyTherad() {

        lock.lock();
        try {
            flag = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        B b = new B();
        Thread threadWait = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    b.waitThread();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Thread signalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                b.notifyTherad();
            }
        });

        threadWait.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        signalThread.start();
    }

}
