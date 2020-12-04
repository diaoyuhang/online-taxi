package com.diao.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:48 2020/11/13
 */
public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpServerCodec());//websocket是基于http协议的
                        pipeline.addLast(new ChunkedWriteHandler());//以块的方式进行传输
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        //将http升级为ws协议，保持长连接
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));

                        pipeline.addLast(new MyTextWebSocketFrameHandler());
                    }
                })
                .bind(8080)
                .sync()
                .channel().closeFuture().sync();
    }
}

class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

//    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Map<String, Channel> channels = new HashMap<>();

    private static int i = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
//        System.out.println(channel.remoteAddress() + ": " + msg.text());
        channel.writeAndFlush(new TextWebSocketFrame("服务器时间:" + LocalDateTime.now() + ":" + msg.text()));
//        String text = msg.text();
//        String[] split = text.trim().split(" ");
//        Channel channel1 = channels.get(split[0]);
//        channel1.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress()+": "+split[1]));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText());
        System.out.println(ctx.channel().id().asShortText());

        channels.put(++i+"", ctx.channel());
    }
}