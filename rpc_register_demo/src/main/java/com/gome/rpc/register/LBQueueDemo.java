package com.gome.rpc.register;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.*;

/**
 * @author zzy
 * @create 2021-08-14-10:35
 */
public class LBQueueDemo {

    public static final Logger LOG = LoggerFactory.getLogger (LBQueueDemo.class);
    public static final LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object> (3);


    public static void main(String[] args) {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder ();
        ThreadFactory build = threadFactoryBuilder.setNameFormat ("the queue thread #%d").build ();
        ExecutorService executorService = Executors.newFixedThreadPool (2, build);
        LBQueueDemo lbQueueDemo = new LBQueueDemo ();

//        ReentrantLock reentrantLock = new ReentrantLock ();
//        try {
//            reentrantLock.lock ();
//        } catch (Exception e) {
//            e.printStackTrace ();
//        } finally {
//            reentrantLock.unlock ();
//        }
        try {
            LOG.info ("before the current queue size is {}", queue.size ());
            queue.put (1);
            queue.put (2);
            queue.put (3);
            LOG.info ("full block the current queue size is {}", queue.size ());
            executorService.submit (new QueueThread (queue));
            queue.put (4);
            LOG.info ("after the current queue size is {}", queue.size ());

            queue.poll ();
            LOG.info ("take1 the current queue size is {}", queue.size ());
            queue.take ();
            LOG.info ("take2 the current queue size is {}", queue.size ());
            queue.take ();
            LOG.info ("take3 the current queue size is {}", queue.size ());
            Object poll = queue.poll ();
            LOG.info ("take4 the current queue size is {}", queue.size ());
            if (poll == null) {
                LOG.info ("the pool result is null");
            }
            boolean offer = queue.offer (5);
            LOG.info ("the current offer result is {}", offer);
            LOG.info ("offer the current queue size is {}", queue.size ());

            Iterator<Object> iterator = queue.iterator ();

            while (iterator.hasNext ()) {
                iterator.next ();
            }
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        try {
            Thread.sleep (3000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        executorService.shutdown ();
        boolean terminated = false;
        try {
            terminated = executorService.awaitTermination (10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }finally {
            if (!terminated) {
                executorService.shutdownNow ();
            }
        }

    }

    // 增加個隊列
    public void addQueue(Object obj){


    }

    public Object takeObj() {
        return  null;
    }

    static class QueueThread implements Runnable{
        LinkedBlockingQueue linkedBlockingQueue;

        public QueueThread(LinkedBlockingQueue queue) {
            this.linkedBlockingQueue = queue;
        }
        public void run() {
            try {
                Thread.sleep (5000);
                Object take = linkedBlockingQueue.take ();
                LOG.info ("the current thread take object is {}", take);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }

        }
    }
}
