package com.example.myandroiddemo.thread.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//写两个线程，一个线程打印1-52，另一个线程打印A-Z，打印结果为12A34B…5152Z
public class D {
    private ReentrantLock lock = new ReentrantLock(false);
    private Condition condition = lock.newCondition();
    private int flag = 1;

    private void printlnNum() {
        int count = 1;
        while (count <= 26) {
            lock.lock();
            try {

                if (flag != 1) {
                    try {
                        System.out.println("数字打印线程 进入等待。。。");
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(2 * count - 1);
                System.out.println(2 * count);
                count++;
                flag = 2;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("数字打印线程 通知其他线程。。。");
                condition.signal();
            } finally {

                System.out.println("数字打印线程 释放锁。。。");
                lock.unlock();
            }
        }
    }

    private void printlnChar() {
        int count = 1;
        while (count <= 26) {
            lock.lock();
            try {
                if (flag != 2) {
                    try {

                        System.out.println("字符打印线程 进入等待。。。");
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println((char)(count - 1 + 'A'));
                count++;
                flag = 1;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("字符打印线程 通知其他线程。。。");
                condition.signal();
            } finally {

                System.out.println("字符打印线程 释放锁。。。");
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        D d = new D();
        Thread waitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                d.printlnNum();
            }
        });

        Thread signalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                d.printlnChar();
            }
        });

        waitThread.start();
        signalThread.start();
    }
}
