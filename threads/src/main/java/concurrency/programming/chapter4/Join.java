package concurrency.programming.chapter4;

import java.sql.Connection;

/**
 * Created by 王忠珂 on 2016/11/22.
 */
public class Join {

    static class InnerJoin implements Runnable {
        public Thread thread;
        public static volatile int i;
        public void run() {
            while (i < 10) {
                System.out.println( Thread.currentThread().getName() + ": " + i);
                i++;
                try {
                    thread.join(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main (String [] args) {
        // 死锁
        InnerJoin innerA = new InnerJoin();
        InnerJoin innerB = new InnerJoin();
        Thread threadA = new Thread(innerA, "thread A");
        Thread threadB = new Thread(innerB, "thread B");
        innerA.thread = threadB;
        innerB.thread = threadA;
        threadA.start();
        threadB.start();
    }
}

