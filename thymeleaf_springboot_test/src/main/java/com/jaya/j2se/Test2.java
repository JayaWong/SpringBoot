package com.jaya.j2se;

import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test2 {
    static int size = 100;
    static  ThreadPoolExecutor realExecutor =
            new ThreadPoolExecutor(size, size,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
                private final AtomicInteger mThreadNum = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r, "Test2-thread-" + mThreadNum.getAndIncrement());
                    thread.setDaemon(true);
                    return thread;
                }
            }, new ThreadPoolExecutor.CallerRunsPolicy());
    public static void main(String[] args) throws Exception {
        System.out.println("bg");
        while (true) {
            byte[] bs = new byte[1024 * 1024];
        }
    }
//    public static void main(String[] args) throws Exception {
//        System.out.println(Runtime.getRuntime().freeMemory());
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        long bg = System.currentTimeMillis();
//        int threadCount = 100;
//        CountDownLatch cdl = new CountDownLatch(threadCount);
//        for (int i = 0; i < threadCount; i++) {
//            realExecutor.execute(()->{
//                int a = 0;
//                for (int j = 0; j < 100; j++) {
//                    for (int k = 0; k < 1; k++) {
//                        a++;
//                        int i1 = a * (100000 - j);
//                        int i2 = i1 / (k + 1);
//                    }
//                    File file = new File("D:\\var\\log\\tangjava\\eams.log");
//                    try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                        String s = null;
//                        while ((s = reader.readLine()) != null) {
//
//                        }                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                cdl.countDown();
//            });
//        }
//        cdl.await();
//        System.out.println("耗时: " + (System.currentTimeMillis() - bg));
//    }
    //消耗cup代码,线程数大于物理核数后性能没有明显提示
//    public static void main(String[] args) throws InterruptedException {
//        long bg = System.currentTimeMillis();
//        CountDownLatch cdl = new CountDownLatch(100000);
//        for (int i = 0; i < 100000; i++) {
//            realExecutor.execute(()->{
//                int a = 0;
//                for (int j = 0; j < 100000; j++) {
//                    for (int k = 0; k < 100000; k++) {
//                        a++;
//                        int i1 = a * (100000 - j);
//                        int i2 = i1 / (k + 1);
//                    }
//                }
//                cdl.countDown();
//            });
//        }
//        cdl.await();
//        System.out.println("耗时: " + (System.currentTimeMillis() - bg));
//    }

}
