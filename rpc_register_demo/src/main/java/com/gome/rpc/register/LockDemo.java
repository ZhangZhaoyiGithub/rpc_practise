package com.gome.rpc.register;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;


/**
 * @author zzy
 * @create 2021-08-15-13:22
 */
public class LockDemo {
    public static final Logger LOG = LoggerFactory.getLogger (LockDemo.class);
    private static final ThreadFactory th = new ThreadFactoryBuilder ().setNameFormat ("lock-Thread-%d").build ();
    public static final ExecutorService executorService = Executors.newFixedThreadPool (5, th);

    public static void main(String[] args) {
        Lock2 lock2 = new Lock2 ();
        for (int i = 0; i < 3; i++) {
            executorService.execute (new Thread1(lock2));
        }

        executorService.shutdown ();
        try {
            if(!executorService.awaitTermination (3000, TimeUnit.MILLISECONDS)){
                executorService.shutdownNow ();
            }
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

    }

    static class Thread1 implements Runnable {
        private Lock2 lock2;

        public Thread1(Lock2 lock2) {
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            methodA ();
            methodB ();
        }

        private void methodA() {
            try {
                lock2.lock ();
                LOG.info ("{} methodA", Thread.currentThread ().getName ());
                lock2.unlock ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        private void methodB() {
            try {
                lock2.lock ();
                LOG.info ("{} methodB", Thread.currentThread ().getName ());
                lock2.unlock ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }
    }

}
