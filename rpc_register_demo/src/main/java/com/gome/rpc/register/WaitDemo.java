package com.gome.rpc.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzy
 * @create 2021-08-14-23:25
 */
public class WaitDemo {
    private static Logger LOG = LoggerFactory.getLogger (WaitDemo.class);

    private static Object objLock = new Object ();

    public static void main(String[] args) {
        LOG.info ("the log begin");
        new Thread1 ().start ();

        try {
            Thread.sleep (300);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        new Thread2 ().start ();

        LOG.info ("the main thread end");

    }

    static class Thread1 extends Thread{
        @Override
        public void run() {
            synchronized (objLock) {
                LOG.info ("the current Thread is {} for wait", Thread.currentThread ().getName ());
                try {
                    objLock.wait ();
                } catch (InterruptedException e) {
                    e.printStackTrace ();
                }

                LOG.info ("the current Thread is {} finished the thread", Thread.currentThread ().getName ());
                LOG.info ("the current Thread is {} finished the thread agin", Thread.currentThread ().getName ());
            }
        }
    }

    static class Thread2 extends Thread{
        @Override
        public void run() {
            synchronized (objLock) {
                LOG.info ("the current Thread {} is running the program", Thread.currentThread ().getName ());
                objLock.notifyAll ();
                LOG.info ("the current Thread release the lock");
            }
                LOG.info ("the current Thread release1 the lock");
        }
    }
}
