package wang.thread;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutor {

	private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
	static ScheduledFuture remove;

	public static void main(String [] args) {
		SyncJob job = new SyncJob(1, TimeUnit.SECONDS, 0);
		remove = executor.scheduleWithFixedDelay(job, job.initalDelay, job.delay, job.timeUnit);
	}

	/**
	 * 关闭定时任务
	 */
	public static void remove() {
		remove.cancel(false);
	}

	static class SyncJob extends TaskJob {
		int cnt = 0;

		SyncJob(int delay, TimeUnit seconds, int initalDelay) {
			this.delay = delay;
			this.timeUnit = seconds;
			this.initalDelay = initalDelay;
		}

		@Override
		public void run() {
			System.out.println("Sync Job is running " + cnt);
			if ( ++ cnt > 3) {
				remove();
			}
		}
	}
}

abstract class TaskJob implements Runnable {

	protected TaskJob(){}
	public TaskJob(int delay, TimeUnit timeUnit, int initalDelay) {
		this.delay = delay;
		this.timeUnit = timeUnit;
		this.initalDelay = initalDelay;
	}

	protected int delay = 1;
	protected TimeUnit timeUnit = TimeUnit.HOURS;
	protected int initalDelay = 0;
	protected volatile boolean running = true;

}
