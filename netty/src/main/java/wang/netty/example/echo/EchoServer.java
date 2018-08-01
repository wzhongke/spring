package wang.netty.example.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import wang.netty.example.CommonServer;

public class EchoServer {

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 9797;
		}
		new CommonServer(port, new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new EchoServerHandler());
			}
		}).run();
	}
}
