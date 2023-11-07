package com.example.myandroiddemo.thread.demo;

import java.lang.reflect.Field;


//写两个线程，一个线程打印1-52，另一个线程打印A-Z，打印结果为12A34B…5152Z
public class C {
    public static void main(String[] args) {
        C c = new C();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; i++) {
                    c.printlnNum();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; i++) {
                    c.printlnChar();
                }
            }
        });

        thread1.start();
        thread2.start();

    }


    int flag = 1;//1:打印数字  2：打印字母
    int count = 1;
    private synchronized void printlnNum() {
        if (flag != 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(2 * count - 1);
        System.out.println(2 * count);
        flag = 2;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        notify();
    }

    private synchronized void printlnChar() {
        if (flag != 2) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println((char)(count - 1 + 'A'));
        count++;
        flag = 1;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        notify();
    }



}
