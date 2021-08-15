package com.gome.rpc.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzy
 * @create 2021-08-15-12:04
 */
public class Count {
    private static final Logger LOG = LoggerFactory.getLogger (Count.class);
    Lock lock = new Lock ();

    public void print() throws InterruptedException {
        lock.lock ();
        LOG.info ("the print begin");
        doAdd ();
        LOG.info ("the print end");
        lock.unlock ();
        LOG.info ("the print finish");
    }

    public void doAdd() throws InterruptedException {
        lock.lock ();
        LOG.info ("doAdd Lock");
        lock.unlock ();
    }

    public static void main(String[] args) throws InterruptedException {
        Count count = new Count ();
        count.print ();
    }

}
