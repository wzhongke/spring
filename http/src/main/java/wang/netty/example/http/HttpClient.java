package wang.netty.example.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HttpClient {

	private Channel channel;

	public void init() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new HttpClientHandler());
					}
				});

			// Start the client.
			ChannelFuture f = b.connect("127.0.0.1", 8081).sync(); // (5)
			f.addListener((ChannelFutureListener) future -> {
				if (future.isSuccess()) {
					System.out.println("连接成功");
				} else {
					System.out.println("连接失败");
					workerGroup.shutdownGracefully();
				}
			});
			this.channel = f.channel();
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		HttpClient client = new HttpClient();
		client.init();
		QueryData data = new QueryData();
		data.setQuery("中国");
		ChannelFuture res = client.channel.writeAndFlush(Unpooled.wrappedBuffer(JSONObject.fromObject(data).toString().getBytes("UTF8"))).sync();
		Thread.sleep(1000);
	}
}
