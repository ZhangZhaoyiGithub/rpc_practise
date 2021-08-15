package com.gome.rpc.register;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zzy
 * @create 2021-08-15-15:51
 */
public class LockDemo02 {
    public static final Logger LOG = LoggerFactory.getLogger (LockDemo02.class);
    private static List list = new ArrayList ();
    private static ReentrantLock lock = new ReentrantLock ();
    private static ThreadFactory th = new ThreadFactoryBuilder ().setNameFormat ("trylock_thread_%d").build ();
    private static ExecutorService executorService = Executors.newFixedThreadPool (5, th);


    public static void main(String[] args) {
        LockDemo02 lockDemo02 = new LockDemo02 ();
        for (int i = 0; i < 3; i++) {
            executorService.execute (new TryLockDemo (i));
        }


        executorService.shutdown ();

        try {
            if(!executorService.awaitTermination (2000, TimeUnit.MILLISECONDS)){
                executorService.shutdownNow ();
            }

        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

            for (Object o : list) {
                LOG.info (o.toString ());
            }
        LOG.info ("the demo into");
    }


    static class TryLockDemo implements Runnable {
        private Object obj;

        public TryLockDemo(Object obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            ReentrantLock lock = LockDemo02.lock;
            boolean result = lock.tryLock ();
            try {
                Thread.sleep (1000);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            LOG.info ("the lock result is {}", result);
            list.add (this.obj);
            if (result) {
                lock.unlock ();
            }
        }
    }
}
