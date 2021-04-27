package com.jaya.j2se;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InterrupteThreadTest {
    public static void main(String[] args) {
        main1(args);
    }
    public static void main0(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1l);
                System.out.println("thread start");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(2l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Exception in thread "main" java.lang.IllegalThreadStateException
         * 	at java.lang.Thread.start(Thread.java:708)
         * 	at com.jaya.j2se.InterrupteThreadTest.main0(InterrupteThreadTest.java:25)
         * 	at com.jaya.j2se.InterrupteThreadTest.main(InterrupteThreadTest.java:7)
         * 	线程运行结束后,不能再次运行了
         */
        thread.start();

    }
    public static class MyRun implements Runnable{
        public  Thread currentThread;
        @Override
        public void run() {
            this.currentThread = Thread.currentThread();
            try {
                TimeUnit.SECONDS.sleep(5l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run over "+new Date().toLocaleString());
        }
    }
    public static void main1(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        System.out.println("main1 start"+new Date().toLocaleString());
        MyRun myRun = new MyRun();
        es.execute(myRun);
        try {
            TimeUnit.SECONDS.sleep(1l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 中断只会被触发一次
         */
        myRun.currentThread.interrupt();
        es.execute(myRun);
        System.out.println("main1 over" + new Date().toLocaleString());
    }
    public static void main4(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        System.out.println("main1 start"+new Date().toLocaleString());
        es.execute(() -> {
            long bg = System.currentTimeMillis();
            while ((System.currentTimeMillis() - bg) / 1000 < 3 && !Thread.currentThread().isInterrupted()) {
                //在不使用sleep和wait方法时,需要调Thread.currentThread().isInterrupted()方法判断是否有中断
            }

            System.out.println("run over "+new Date().toLocaleString());
        });
        es.execute(() -> {
            long bg = System.currentTimeMillis();
            while ((System.currentTimeMillis() - bg) / 1000 < 3 && !Thread.currentThread().isInterrupted()) {
                //在不使用sleep和wait方法时,需要调Thread.currentThread().isInterrupted()方法判断是否有中断
            }

            System.out.println("run over1 "+new Date().toLocaleString());
        });
        List<Runnable> runnables = es.shutdownNow();
        System.out.println(runnables.size());
        try {
            TimeUnit.SECONDS.sleep(1l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 中断只会被触发一次
         */
        try {
            es.awaitTermination(10,TimeUnit.SECONDS);
            System.out.println("awaitTermination start" + new Date().toLocaleString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void main3(String[] args) {
        Thread thread = new Thread(() -> {
            long bg = System.currentTimeMillis();
            while ((System.currentTimeMillis() - bg) / 1000 < 10 && !Thread.currentThread().isInterrupted()) {
                //在不使用sleep和wait方法时,需要调Thread.currentThread().isInterrupted()方法判断是否有中断
            }
        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(1l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 中断只会被触发一次
         */
        thread.interrupt();

    }

    public static void main2(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10l);
                System.out.println("thread start");
            } catch (InterruptedException e) {
                //这里会触发
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
//            try {
//                TimeUnit.SECONDS.sleep(10l);
//                System.out.println("thread start");
//            } catch (InterruptedException e) {
//                //这里不会触发
//                e.printStackTrace();
//            }
            System.out.println("thread over");

        });
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(2l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 中断只会被触发一次
         */
        thread.interrupt();

    }

}
