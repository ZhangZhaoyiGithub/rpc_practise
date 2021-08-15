package com.gome.rpc.register;

/**
 * @author zzy
 * @create 2021-08-15-10:08
 */
public class Sync {
    public void test() {
        synchronized (Sync.class) {
            System.out.println("test start " + Thread.currentThread ().getName ());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test end " + Thread.currentThread ().getName ());
        }
    }
}
