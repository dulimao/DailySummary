package com.example.myandroiddemo.thread.demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class F {

    //生产者消费者模式

    private Queue<Integer> queue = new LinkedList();
    private final int LIMIT = 10;

    //生产者方法
    private void product() {
        int value = 0;
        while (true) {
            synchronized (queue) {
                if (queue.size() > LIMIT) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int v = value++;
                System.out.println(Thread.currentThread().getName() + " 生产了 " + v);
                queue.offer(v);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.notifyAll();
            }
        }
    }


    //消费者方法
    private void consume() {
        while (true) {
            synchronized (queue) {
                if (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Integer poll = queue.poll();
                System.out.println(Thread.currentThread().getName() + " 消费了 " + poll);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.notifyAll();
            }
        }
    }


    public static void main(String[] args) {
        F f = new F();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                f.product();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                f.consume();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                f.consume();
            }
        });

        t1.start();
        t2.start();
        t3.start();


    }

}
