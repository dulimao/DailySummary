package com.example.myandroiddemo.thread.demo;

public class E {

    int i = 0;

    private synchronized void printlnJ() {
        while (true) {
            if (i % 2 != 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " :  " + i);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notify();
        }
    }

     private synchronized void printlnO() {
        while (true) {

            if (i % 2 != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " :  " + i);
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notify();
        }
     }

    public static void main(String[] args) {
        E e = new E();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                e.printlnJ();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                e.printlnO();
            }
        });
        t1.start();
        t2.start();
    }
}
