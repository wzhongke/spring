package wang.netty.example.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author wangzhongke
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline p = socketChannel.pipeline();
		/**
		 * http-request解码器
		 * http服务器端对request解码
		 */
		p.addLast("decoder", new HttpRequestDecoder());
		p.addLast("servercodec", new HttpServerCodec());
		p.addLast("aggregator", new HttpObjectAggregator(1048576));
		p.addLast(new HttpServerHandler());
		/**
		 * http-response解码器
		 * http服务器端对response编码
		 */
		p.addLast("responseencoder", new HttpResponseEncoder());
	}
}
