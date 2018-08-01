package wang.netty.example.time;

import wang.netty.example.CommonServer;

public class TimeServer {

	public static void main (String [] args) throws InterruptedException {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 9797;
		}
		new CommonServer(port, new TimeServerHandler()).run();
	}
}
