package wang.netty.example.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

    /**
     * 在此释放 BytebBuf
     * @param ctx
     */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
	    // 写完毕后关闭连接
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
				.addListener(ChannelFutureListener.CLOSE);
	}
}
