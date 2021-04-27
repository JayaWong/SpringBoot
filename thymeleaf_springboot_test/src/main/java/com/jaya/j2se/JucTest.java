package com.jaya.j2se;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class JucTest {
    public static void main(String[] args) throws InterruptedException {
        main0(args);
    }
    public static void main0(String[] args) throws InterruptedException {
        System.out.println("main start " + new Date().toLocaleString());
        ExecutorService es = Executors.newFixedThreadPool(10);
        MyRun2 myRun = new MyRun2();
        for (int i = 0; i < 10; i++) {
            es.execute(myRun);
        }
        System.out.println("execute start" + new Date().toLocaleString());
        System.out.println("main over" + new Date().toLocaleString());
        es.shutdown();
        es.awaitTermination(30, TimeUnit.SECONDS);
    }

    /**
     *
     */
    public static class MyRun1 implements Runnable {
        public static volatile Thread thread;

        @SneakyThrows
        @Override
        public void run() {
            thread = Thread.currentThread();
            System.out.println(thread.getName() + ":run start" );
            LockSupport.park(this);
            System.out.println(thread.isInterrupted());
            System.out.println(thread.getName() + ":run over" );
        }
    }

    /**
     * 两个线程交替打印
     */
    public static class MyRun implements Runnable{
        public static volatile LinkedBlockingDeque<Thread> threads = new LinkedBlockingDeque<Thread>();
        private int[] a = {1, 3, 5, 7, 9};
        private int[] b = {2, 4, 6, 8, 10};
        private AtomicBoolean bg = new AtomicBoolean(false);
        private AtomicBoolean lock = new AtomicBoolean(false);

        @SneakyThrows
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            threads.offer(thread);
            boolean andSet = bg.getAndSet(true);
            System.out.println(thread.getName() + ":" + andSet);
            int[] n = andSet ? b : a;
            for (int i = 0; i < n.length; i++) {
//                TimeUnit.SECONDS.sleep(1);
                if (i == 0 && andSet) {
                    LockSupport.park(this);
                }
                System.out.println(n[i]);
                while (i == 0 && threads.size() != 2) {
                    Thread.yield();
                }
                for (Thread thread1 : threads) {
                    if (thread1 != thread) {
                        LockSupport.unpark(thread1);
                    }
                }
                if (i != (n.length - 1) || !andSet) {
                    LockSupport.park(this);
                }
            }
            System.out.println(thread.getName() + ":" + threads.toString());
            if (andSet) {
                threads.clear();
            }
            System.out.println(thread.getName() + ":" + threads.toString());
        }
    }
    /**
     * 两个线程交替打印
     */
    public static class MyRun2 implements Runnable{
        private int i = 0;
        private AtomicBoolean bg = new AtomicBoolean(false);
        MyLock myLock = new MyLock();
        private boolean see = false;
        @SneakyThrows
        @Override
        public void run() {
            Thread thread = Thread.currentThread();

            System.out.println(thread.getName() + ":start run ");
            for (int j = 0; j < 10000; j++) {
                try {
//                    myLock.lock();
                    i++;
                } finally {
//                    myLock.unlock();
                }

            }
            if (see) {
                System.out.println(thread.getName() + ":over run:" + i + ":true");
            } else {
                System.out.println(thread.getName() + ":over run:" + i + ":false" );
            }
            if (thread.getName().equals("pool-1-thread-1")) {
                see = true;
            }
        }
    }

    public static class MyLock implements Lock {

        private Queue<Thread> threads = new ConcurrentLinkedQueue();
        private AtomicBoolean lock = new AtomicBoolean(false);

        @Override
        public void lock() {
            Thread curThread = Thread.currentThread();
            threads.offer(curThread);
            boolean interrupted = false;
            while (threads.peek() != curThread || !lock.compareAndSet(false, true)) {
                LockSupport.park(this);
                interrupted = curThread.isInterrupted();
            }
            threads.remove();
            if (interrupted) {
                curThread.interrupt();
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            lock.set(false);
            LockSupport.unpark(threads.peek());
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }
}
