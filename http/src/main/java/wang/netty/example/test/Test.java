package wang.netty.example.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.Date;

class ServerHandler  extends ChannelHandlerAdapter {

	//每当从客户端收到新的数据时，这个方法会在收到消息时被调用
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf in = (ByteBuf) msg;
		try {
			// Do something with msg
			System.out.println("server get :" + in.toString(CharsetUtil.UTF_8));

			ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.copiedBuffer(("server send time: " + new Date()).getBytes()));

			//服务端发送数据完毕后,关闭通道
			channelFuture.addListener(ChannelFutureListener.CLOSE);

		} finally {
			//ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放
			//or ((ByteBuf)msg).release();
			ReferenceCountUtil.release(msg);
		}

	}

	//exceptionCaught()事件处理方法是当出现Throwable对象才会被调用
	//当Netty由于IO错误或者处理器在处理事件时抛出的异常时
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();

	}

}

public class Test {
	public static void main(String[] args) {

		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // (2)
		int port = 8867;
		try {
			ServerBootstrap b = new ServerBootstrap(); // (3)
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class) // (4)
				.childHandler(new ChannelInitializer<SocketChannel>() { // (5)
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new ServerHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)          // (6)
				.childOption(ChannelOption.SO_KEEPALIVE, true); // (7)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync(); // (8)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			System.out.println("start server....");
			f.channel().closeFuture().sync();
			System.out.println("stop server....");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("exit server....");
		}

	}
}
