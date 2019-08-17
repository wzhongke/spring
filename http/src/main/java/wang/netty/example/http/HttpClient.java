package wang.netty.example.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HttpClient {


	static HttpClientHandler clientHandler = new HttpClientHandler();

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
						ChannelPipeline p = ch.pipeline();
						p.addLast(new HttpClientCodec());
						//聚合
						p.addLast(new HttpObjectAggregator(1024 * 10 * 1024));

						//解压
						p.addLast(new HttpContentDecompressor());
						p.addLast(clientHandler);
						p.addLast(new HttpResponseDecoder());

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
		} catch (Exception e) {

		}
	}

	static HttpRequest getMessage() throws UnsupportedEncodingException, URISyntaxException {
		QueryData data = new QueryData();
		data.setQuery("中国");
		data.setFrom("web");
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(
			HttpVersion.HTTP_1_1, HttpMethod.POST, new URI("http://127.0.0.1:8081/").getRawPath());
		request.headers().set(HttpHeaderNames.HOST, "127.0.0.1");
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
		request.headers().set(HttpHeaderNames.CONTENT_TYPE,"application/json");
		request.content().writeBytes(JSONObject.fromObject(data).toString().getBytes(CharsetUtil.UTF_8.name()));
		request.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
		return request;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		HttpClient client = new HttpClient();
		client.init();
		QueryData data = new QueryData();
		data.setQuery("中国");
		for (int i=0;i<20;i++) {
			ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
			timer.scheduleWithFixedDelay(() -> {
				try {
					clientHandler.sendMessage(getMessage());
				} catch (UnsupportedEncodingException | URISyntaxException e) {
					e.printStackTrace();
				}
			}, 1, 10, TimeUnit.MICROSECONDS);
		}
	}
}
