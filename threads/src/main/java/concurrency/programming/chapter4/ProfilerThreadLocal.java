package concurrency.programming.chapter4;

public class ProfilerThreadLocal {
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
		protected Long initalValue () {
			return System.currentTimeMillis();
		}
	};

	public static final void begin () {
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}

	public static final long end () {
		return System.currentTimeMillis() - TIME_THREADLOCAL.get();
	}
}
