package wang.netty.example.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;


public class HttpClientHandler extends SimpleChannelInboundHandler<Object> {

	private ChannelHandlerContext ctx;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("channel active");
		QueryData data = new QueryData();
		data.setQuery("中国");
		ctx.writeAndFlush(Unpooled.copiedBuffer(JSONObject.fromObject(data).toString().getBytes("UTF8")));
		this.ctx = ctx;
	}

	public void sendMessage(Object messge) {
		if (ctx != null) {
			ctx.writeAndFlush(messge);
		}
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("msg: " + msg);
	}
}
