package concurrency.programming.chapter4;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {

	public static void main(String [] args) throws IOException {
		PipedWriter out = new PipedWriter();
		PipedReader in = new PipedReader();
		// 连接输入和输出流，否则在使用时会抛出 IOException
		in.connect(out);
		Thread printThread = new Thread(new Print(in), "PrintThread");
		printThread.start();
		int receive;
		while ((receive = System.in.read()) != -1) {
			out .write(receive);
		}
		out.close();
	}

	static class Print implements Runnable {

		private PipedReader in;

		public Print(PipedReader in) {
			this.in = in;
		}

		@Override
		public void run() {
			int receive;
			try {
				while ((receive = in.read()) != -1) {
					System.out.print((char) receive);
				}
				System.out.println();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
