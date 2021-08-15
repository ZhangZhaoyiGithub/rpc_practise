package com.gome.rpc.register;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zzy
 * @create 2021-08-14-21:35
 */
public class QueueAndLockDemo {
    private static final Logger LOG = LoggerFactory.getLogger (QueueAndLockDemo.class);

    // 寫鎖
    private static final ReentrantLock putLock = new ReentrantLock ();

    // 讀鎖
    private static final ReentrantLock takeLock = new ReentrantLock ();

    // 開啓綫程
    private static final ThreadFactory th = new ThreadFactoryBuilder().setNameFormat ("queue thread #%d").build ();
    private static final ExecutorService executorService = Executors.newFixedThreadPool (5, th);

   private static Node root = new Node(null);

   private static final AtomicInteger count = new AtomicInteger ();

    public static void main(String[] args) {
        LOG.info ("the current name");
        try {
            root.wait (3000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        LOG.info ("the wait finished");
        executorService.execute (() -> put ());
        executorService.execute (() -> put ());


        executorService.shutdown ();
        try {
            executorService.awaitTermination (2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } finally {
            executorService.shutdownNow ();
        }


    }

    private static void put() {
        try {
            putLock.lock ();
            Random random = new Random ();
            LOG.info ("the random number is {}", random.nextInt (100));
            root.next = new Node (new Integer (random.nextInt (100)));
            root = root.next;
            count.getAndIncrement ();
//            putLock.wait (100);
//            Thread.sleep (999);
            LOG.info ("the data size is {}, the data is {}", count.get (), root);
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            putLock.unlock ();
        }
    }

    static class Node<E> {
        E e;
        Node<E> next;

        public Node(E e) {
            this.e = e;
        }
    }
}
