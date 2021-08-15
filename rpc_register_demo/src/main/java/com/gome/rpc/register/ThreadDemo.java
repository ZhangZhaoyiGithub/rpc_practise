package com.gome.rpc.register;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zzy
 * @create 2021-08-15-10:08
 */
public class ThreadDemo {
    private static final Logger LOG = LoggerFactory.getLogger (ThreadDemo.class);
    private static final ThreadFactory th = new ThreadFactoryBuilder ().setNameFormat ("lock thread #%d").build ();
    private static final ExecutorService executorService = Executors.newFixedThreadPool (5, th);

    ReentrantLock lock = new ReentrantLock ();

    public static void main(String[] args) {
//        Sync sync = new Sync ();
//        for (int i = 0; i < 3; i++) {
//            executorService.execute (new LockDemo (sync));
//        }
//
//        executorService.shutdown ();
//        try {
//            boolean flag = executorService.awaitTermination (200, TimeUnit.SECONDS);
//            if (!flag) {
//                executorService.shutdownNow ();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace ();
//        }'
        ThreadDemo threadDemo = new ThreadDemo ();
        threadDemo.methodA ();
    }

    static class LockDemo implements Runnable {
        private Sync sync;

        public LockDemo(Sync sync) {
            this.sync = sync;
        }

        public void run() {
            LOG.info ("{} begin", Thread.currentThread ().getName ());
            sync.test ();
        }
    }

    public synchronized void methodA(){
//        lock.lock();
        LOG.info ("the A begin");
        methodB();
        LOG.info ("the C end");
//        lock.unlock();
    }
    public synchronized void methodB(){
//        lock.lock();
        LOG.info ("the B end");
//        lock.unlock();
    }

}
