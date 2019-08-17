package wang.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONObject;
import wang.netty.example.http.QueryData;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * @author wangzhongke
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("channel read");
		ByteBuf m = (ByteBuf) msg;
		try {
			System.out.println(m);
			ctx.close();
		} finally {
			m.release();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx ) {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

