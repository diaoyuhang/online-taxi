package com.diao.server.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 13:52 2020/11/12
 */
public class GroupChatClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        ChannelFuture channelFuture = bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new GroupChatClientInitializer())
                .connect(args[0], Integer.parseInt(args[1]))
                .sync();

        Channel channel = channelFuture.channel();

        Scanner scanner = new Scanner(System.in);
        Boolean flag = true;
        while (flag) {
            String s = scanner.nextLine();
            if ("exit".equals(s.trim()))
                flag = false;

            channel.writeAndFlush(s);
        }

        channelFuture
                .channel().closeFuture().sync();
    }
}

class GroupChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new GroupChatClientHandler());
    }
}

class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
