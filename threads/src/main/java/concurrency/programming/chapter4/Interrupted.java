package concurrency.programming.chapter4;

import java.util.concurrent.TimeUnit;

public class Interrupted {
	public static void main(String [] args) throws InterruptedException {
		Thread sleepThread =  new Thread(new SleepRunner(), "SleepRunner");
		sleepThread.setDaemon(true);
		Thread busyThread = new Thread(new BusyRunner(), "BusyRunner");
		busyThread.setDaemon(true);
		sleepThread.start();
		busyThread.start();
		TimeUnit.SECONDS.sleep(1);
		sleepThread.interrupt();
		busyThread.interrupt();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("SleepThread interrupted is: " + sleepThread.isInterrupted());
		System.out.println("BusyRunner interrupted is: " + busyThread.isInterrupted());
		TimeUnit.SECONDS.sleep(2);
	}


	// 当另一个线程调用interrupt()中断该线程时，该线程会从阻塞状态退出并且抛出中断异常
	static class SleepRunner implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					// 许多抛出 InterruptedException 的方法在抛出 InterruptedException 前，java虚拟机会先将该线程的中断标志位清楚
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt(); //这样处理比较好，会在抛出异常后，设置中断标志位。
				}
			}
		}
	}

	static class BusyRunner implements Runnable {

		@Override
		public void run() {
			while (true) {

			}
		}
	}
}
