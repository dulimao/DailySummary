package com.example.myandroiddemo.thread.demo;

public class G {

    private Object lock1 = new Object();
    private Object lock2 = new Object();


    private void product() {
        synchronized (lock1) {
            System.out.println("生产者线程 + " + Thread.currentThread().getName() + " 持有了lock1" );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println("生产者线程 + " + Thread.currentThread().getName() + " 持有了lock2");
            }

        }
    }

    private void consume() {
        synchronized (lock2) {
            System.out.println("消费者线程 + " + Thread.currentThread().getName() + " 持有了lock2" );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock1) {
                System.out.println("消费者线程 + " + Thread.currentThread().getName() + " 持有了lock1");
            }

        }
    }

    public static void main(String[] args) {
        G g = new G();
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
