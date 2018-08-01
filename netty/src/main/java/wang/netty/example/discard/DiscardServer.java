package wang.netty.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

	private int port;

	public DiscardServer(int port) {
		this.port = port;
	}

	public void run () throws InterruptedException {
		// NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器
		// Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议
		// boss 用来接收进来的连接，并将接收的连接注册到 worker 中
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 用来处理已经被接收的连接
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new DiscardServerHandler());
					}
				})
				// option 提供给NioServerSocketChannel用来接收进来的连接
				.option(ChannelOption.SO_BACKLOG, 128)
				// childOption 是对父管道ServerChannel接收到的连接的配置
				.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync();

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 9797;
		}
		new DiscardServer(port).run();
	}
}
