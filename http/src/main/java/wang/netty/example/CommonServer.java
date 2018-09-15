package wang.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;

public class CommonServer {

	private int port;
	private ChannelHandlerAdapter handler;

	public CommonServer(int port) {
		this.port = port;
	}

	public CommonServer(int port, ChannelHandlerAdapter handler) {
		this.port = port;
		this.handler = handler;
	}

	public void run () throws InterruptedException {
		// NioEventLoopGroup 是用来处理I/O操作的多线程事件循环器
		// Netty提供了许多不同的EventLoopGroup的实现用来处理不同传输协议
		// boss 用来接收进来的连接，并将接收的连接注册到 worker 中
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 用来处理已经被接收的连接
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		// 设置 内存泄漏检测级别，检测需要调用 release() 的 ByteBuf
		// 或者在启动参数中加入： java -Dio.netty.leakDetectionLevel=ADVANCED，这个错误信息更详细
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(handler)
				// option 提供给NioServerSocketChannel用来接收进来的连接
				.option(ChannelOption.SO_BACKLOG, 128)
				// childOption 是对父管道ServerChannel接收到的连接的配置
				.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			System.out.println("Server listen port " + port);
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
}
