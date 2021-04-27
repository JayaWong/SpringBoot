package com.jaya.j2se;

import lombok.SneakyThrows;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {
        main1(args);
    }
    public static void main1(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1, true);
        System.out.println(semaphore.tryAcquire());
        System.out.println(semaphore.tryAcquire());
    }

    public static void main0(String[] args) throws InterruptedException {
        System.out.println("main start " + new Date().toLocaleString());
        ExecutorService es = Executors.newFixedThreadPool(10);
        MyRun myRun = new MyRun();
        for (int i = 0; i < 2; i++) {
            es.execute(myRun);
        }
        System.out.println("execute start" + new Date().toLocaleString());
        System.out.println("main over" + new Date().toLocaleString());
        es.shutdown();
        es.awaitTermination(30, TimeUnit.SECONDS);
    }
    /**
     * 两个线程交替打印
     */
    public static class MyRun implements Runnable{
        private int[] a = {1, 3, 5, 7, 9};
        private int[] b = {2, 4, 6, 8, 10};
        private AtomicBoolean bg = new AtomicBoolean(false);
        private Semaphore semaphore = new Semaphore(1, true);
        @SneakyThrows
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            boolean andSet = bg.getAndSet(true);
            System.out.println(thread.getName() + ":" + andSet);
            int[] n = andSet ? b : a;
            for (int i = 0; i < n.length; i++) {
//                TimeUnit.SECONDS.sleep(1);
                semaphore.acquire();
//                TimeUnit.SECONDS.sleep(1);
//                if (i == 0 && andSet) {
//                    semaphore.release();
//                    semaphore.acquire();
//                }
                System.out.println(n[i]);
                semaphore.release();
            }
            System.out.println(thread.getName() + ":");
        }
    }

}
