package algorithms;

import java.io.IOException;

public class ThreadTest {

	public static void main (String [] args) throws InterruptedException, IOException {
		Running run = new Running();

		for (int i=0; i< 10; i++) {
			new Thread (run).start();
		}
		int i = System.in.read();
		run.stop();
		System.in.read();
	}
}

class Running implements Runnable {

	private boolean stop = false;

	public void stop() {
		stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			// do something
			try {
				System.out.println("running");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
