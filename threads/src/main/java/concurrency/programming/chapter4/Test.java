package concurrency.programming.chapter4;

import com.wang.chapter5.ConditionUseCase;
import com.wang.chapter5.TwinsLock;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 王忠珂 on 2016/11/27.
 */
public class Test {
    public static void test() throws InterruptedException {
        final Lock  lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        class Worker extends Thread {
            public void run () {
                while (true) {
                    lock.lock();

                    try {
                        Thread.sleep(1000);
                        System.out.println("Thread currentThread: "  + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        for (int i =0; i<10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }
        for (int i = 0; i<10;i++) {
            Thread.sleep(1000);
            System.out.println();
        }
    }
    static class Worker2 extends Thread {
        public final static String wait = "";
        public void run () {
            while (true) {
                synchronized (wait) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Thread currentThread: " + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void  main(String [] args) throws InterruptedException {
        test();
        ConditionUseCase c = new ConditionUseCase();
        c.conditionWait();
        c.conditionSignal();
    }
}
