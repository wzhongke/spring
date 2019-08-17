package wang.netty.example.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolMap;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import net.sf.json.JSONObject;
import wang.netty.example.http.QueryData;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

class ClientHandler extends ChannelHandlerAdapter {
	private ChannelHandlerContext ctx;
	private ChannelPromise promise;
	private String data = "";
	private long readByte;
	private long contentLength;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	public ChannelPromise sendMessage(Object messge) {
		if (ctx != null) {
			promise = ctx.writeAndFlush(messge).channel().newPromise();
		}
		return promise;
	}
	public String getData() {
		return data;
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
			contentLength = response.headers().getLong(HttpHeaderNames.CONTENT_LENGTH);
			readByte = 0;
			System.err.println("STATUS: " + response.status());
			System.err.println("VERSION: " + response.protocolVersion());
			System.err.println();
		}
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			ByteBuf buf = content.content();
			readByte += buf.readableBytes();
			data += buf.toString(Charset.forName("UTF8"));
			if (readByte >= contentLength) {
				promise.setSuccess();
			}
			buf.release();
		}
	}
}

public class Client {

	public static ChannelPoolMap<String, FixedChannelPool> poolMap;
	private static final Bootstrap bootstrap = new Bootstrap();

	static {
		bootstrap.group(new NioEventLoopGroup());
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.remoteAddress("127.0.0.1", 8081);
	}

	public void init() {
		poolMap = new AbstractChannelPoolMap<String, FixedChannelPool>() {
			@Override
			protected FixedChannelPool newPool(String key) {
				ChannelPoolHandler handler = new ChannelPoolHandler() {
					/**
					 * 使用完channel需要释放才能放入连接池
					 */
					@Override
					public void channelReleased(Channel ch) throws Exception {
						// 刷新管道里的数据
						// ch.writeAndFlush(Unpooled.EMPTY_BUFFER); // flush掉所有写回的数据
						System.out.println("channelReleased......");
					}

					/**
					 * 当链接创建的时候添加channelhandler，只有当channel不足时会创建，但不会超过限制的最大channel数
					 *
					 */
					@Override
					public void channelCreated(Channel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new HttpClientCodec());
						//聚合
						p.addLast(new HttpObjectAggregator(1024 * 10 * 1024));

						//解压
						p.addLast(new HttpContentDecompressor());
						p.addLast(new ClientHandler());
						p.addLast(new HttpResponseDecoder());
					}

					/**
					 *  获取连接池中的channel
					 *
					 */
					@Override
					public void channelAcquired(Channel ch) throws Exception {
						System.out.println("channelAcquired......");
					}
				};

				return new FixedChannelPool(bootstrap, handler, 5); //单个host连接池大小
			}
		};

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

	public static ChannelFuture doConnect(Bootstrap b) {
		return b.connect("127.0.0.1", 8081).addListener((ChannelFuture  f) -> {
			if (!f.isSuccess()) {
				f.channel().eventLoop().schedule(() -> {
					doConnect(b);
				}, 50,TimeUnit.MICROSECONDS);
			}
		});
	}

	public static void main(String[] args) {

		EventLoopGroup group = new NioEventLoopGroup();
		ClientHandler handler = new ClientHandler();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline p = ch.pipeline();
						p.addLast(new HttpClientCodec());

						//聚合
						p.addLast(new HttpObjectAggregator(1024 * 10 * 1024));

						//解压
						p.addLast(new HttpContentDecompressor());
						p.addLast(handler);
						p.addLast(new HttpResponseDecoder());
					}
				});

			// Start the client.

			Channel f = doConnect(b).sync().channel();
			// Prepare the HTTP request.
			ChannelPromise future = handler.sendMessage(getMessage());
			future.await();
			System.out.println(handler.getData());
			ChannelPromise future1 = handler.sendMessage(getMessage());
			future1.await();
			System.out.println(handler.getData());

			// Set some example cookies.

//			// Send the HTTP request.
//			ch.writeAndFlush(request);
//
//			f.channel().writeAndFlush(Unpooled.wrappedBuffer(JSONObject.fromObject(data).toString().getBytes("UTF8")));
//			// Wait until the connection is closed.
			f.closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Shut down the event loop to terminate all threads.
			group.shutdownGracefully();
		}

	}
}
