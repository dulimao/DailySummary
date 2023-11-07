package com.example.myandroiddemo.thread.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class A {
    static int count = 0;
    static Lock rLock = new ReentrantLock(false);
    static Condition condition = rLock.newCondition();
    public static void main(String[] args) {

        Object lock = new Object();


        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    if (count < 10) {
                        try {
                            System.out.println("线程A进入等待状态");
                            lock.wait();
                            System.out.println("线程A恢复了 count: " + count);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    while (true) {
                        if (count < 10) {
                            System.out.println("线程B count: " + count);
                            count++;

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("线程B 即将释放锁");
                            lock.notify();
                            System.out.println("现成B 已释放锁");
                            break;
                        }
                    }
                }
            }
        });

//        threadA.start();
//        threadB.start();

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                rLock.lock();
                if (count < 10) {
                    System.out.println("线程C进入等待。。。");
                    try {
                        condition.await();//会释放锁
                        System.out.println("线程C收到通知 count: " + count);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    finally {
                        System.out.println("线程C 最后释放锁");
                        rLock.unlock();
                    }
                }
            }
        });

        Thread threadD = new Thread(new Runnable() {
            @Override
            public void run() {
                rLock.lock();
                try {


                while (true) {
                    if (count < 10) {
                        count++;
                        System.out.println("线程D count: " + count);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("线程D 即将释放锁");
                        condition.signal();
                        System.out.println("线程D 最后释放锁");
                        Thread.sleep(5000);
//                        rLock.unlock();
                        break;
                    }
                }
                } catch (Exception e) {

                } finally {
                    rLock.unlock();

                }
            }
        });


        threadC.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadD.start();

    }





}
