package com.lqq.ticket;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SellTickets {
    public static void main(String[] args) {
        Users users = new Users();
        Thread thread1 = new Thread(users);
        Thread thread2 = new Thread(users);

        thread1.start();
        thread2.start();
    }
}

class Users implements Runnable {
    private static int count = 10000;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (count >= 1000) {

                    count -= 1000;
                    System.out.println(Thread.currentThread().getName() + "取钱" + " 还剩" + count);
                } else {
                    break;
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

