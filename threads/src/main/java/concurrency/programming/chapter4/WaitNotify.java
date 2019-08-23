package concurrency.programming.chapter4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {

	private static boolean flag = true;
	private static final Object lock = new Object();

	public static void main(String [] args) throws InterruptedException {
		Thread waitThread = new Thread(new Wait(), "WaitThread");
		waitThread.start();
		TimeUnit.SECONDS.sleep(2);
		Thread notifyThread = new Thread (new Notify(), "NotifyThread");
		notifyThread.start();
	}

	static class Wait implements Runnable {

		@Override
		public void run() {
			synchronized (lock) {
				while (flag) {
					try {
						System.out.println(Thread.currentThread() + " flag is true. " +
							"wait @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.wait();          // 释放锁
						System.out.println("Continue execute");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread() + " flag is false. " +
					"running @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
	}

	static class Notify implements Runnable {

		@Override
		public void run() {
			synchronized (lock) {
				System.out.println(Thread.currentThread() + " hold lock. notify @ "
					+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
				lock.notifyAll();
				flag = false;
				try {
					TimeUnit.SECONDS.sleep(5);  // 不释放锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			synchronized (lock) {
				System.out.println(Thread.currentThread() + " hold lock again. sleep @ "
					+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
