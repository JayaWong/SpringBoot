package com.jaya.j2se;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(20, new ThreadFactory() {
            private AtomicInteger i = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(i.incrementAndGet()+"");
//                thread.setDaemon(true);
                return thread;
            }
        });
        MyRun myRun = new MyRun(new Data());
        for (int i = 0; i < 20; i++) {
            executorService.execute(myRun);
        }
        executorService.shutdown();

    }

    public static class MyRun implements Runnable {

        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        private Data data;
        public MyRun(Data data) {
            this.data = data;
        }
        @Override
        public void run() {
            long bg = System.currentTimeMillis();
            String name = Thread.currentThread().getName();
            int i = Integer.parseInt(name);

            if (i % 5 != 0) {
                ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
                try {
                    readLock.lock();
                    TimeUnit.SECONDS.sleep(1);
//                    System.out.println(this.data.a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            } else {
                ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
                try {
                    writeLock.lock();
                    TimeUnit.SECONDS.sleep(1);
                    this.data.a++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }
            System.out.println((System.currentTimeMillis() - bg) / 1000);
        }
    }
    public static class Data {

        private int a = 0;

    }
}
