package com.gome.rpc.register;

/**
 * @author zzy
 * @create 2021-08-15-12:03
 */
public class Lock {
    private boolean isLocked = false;

    public Lock() {
    }

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait ();
        }
        isLocked = true;

    }

    public synchronized void unlock() {
        isLocked = false;
        notify ();
    }

}

class Lock1 {
    boolean isLocked = false;
    Thread lockedBy = null;
    int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread thread = Thread.currentThread ();
        while (isLocked && lockedBy != thread) {
            wait ();
        }
        isLocked = true;
        lockedCount++;
        lockedBy = thread;
    }

    public synchronized void unlock() {
        if (Thread.currentThread () == this.lockedBy) {
            lockedCount--;
            if (lockedCount == 0) {
                isLocked = false;
                notify ();
            }
        }
    }
}

class Lock2 {
    // 鎖獲取標是
    // 傳入線程對象
    // 衝入計數
    private boolean flag = false;
    private Thread currentThread = null;
    private int count = 0;

    public synchronized void lock() throws InterruptedException {
        Thread thread = Thread.currentThread ();
        if (flag && thread != currentThread) {
            this.wait ();
        }

        // 如果沒有獲取 怎flag 為false並且當前線程為空
        currentThread = thread;
        flag = true;
        count++;
    }

    public synchronized void unlock() throws InterruptedException {
        Thread thread = Thread.currentThread ();
        if (thread == currentThread) {
            count--;
            if (count == 0) {
                currentThread = null;
                flag = false;
                this.notifyAll ();
            }
        }
    }


}

