package wang.netty.example.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	/**
	 * channelActive 会在连接建立并准备进行通信时被调用
	 * @param ctx
	 */
	@Override
	public void channelActive (final ChannelHandlerContext ctx) {
		final ByteBuf time = ctx.alloc().buffer(4);
		time.writeInt((int) (System.currentTimeMillis() / 1000L) );
		final ChannelFuture f = ctx.writeAndFlush(time);
		f.addListener((ChannelFutureListener) future -> {
			assert f == future;
			ctx.close();
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
