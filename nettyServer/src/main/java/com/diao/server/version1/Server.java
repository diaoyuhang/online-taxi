package com.diao.server.version1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.ReferenceCountUtil;
import jdk.jfr.Description;

import java.util.List;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:06 2020/11/6
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                //父线程组的通道类型
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer())
                .bind(8080)
                .sync()
                .channel().closeFuture().sync();
    }
}

class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        //获取管道
        ChannelPipeline pipeline = ch.pipeline();
        //给管道添加处理逻辑，消息编解码器
        pipeline.addLast(new MessageCodec());

        pipeline.addLast(new ServerChannelHandler());
    }
}

class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    private static final String message = "消息成功接收!";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println(ctx.name() + "激活");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof MyMessage) {
                MyMessage m = (MyMessage) msg;
                System.out.println(m);
            }


        } finally {
            if (msg != null)
                ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new MyMessage(message, message.getBytes().length));
    }

    /**
     * 捕获到处理过程中的异常
     * 可在这个阶段，将当前有异常的通道给关闭了
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}

class MessageCodec extends ByteToMessageCodec<MyMessage> {
    private final static int LENGTH = 4;

    protected void encode(ChannelHandlerContext ctx, MyMessage msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getMessage().getBytes());

    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        int length = in.readInt();
        in.markReaderIndex();
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[length];
        in.readBytes(data);

        String s = new String(data);
        out.add(new MyMessage(s, s.getBytes().length));
    }
}

class MyMessage {
    private int length;
    private String message;

    public MyMessage(String message, int length) {
        this.message = message;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
