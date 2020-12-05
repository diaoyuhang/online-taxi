package com.diao.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:54 2020/11/4
 */
public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
//                            pipeline.addLast(new HttpServerCodec()); //netty框架自带的http编解码器
                            pipeline.addLast(new HttpRequestDecoder());
                            pipeline.addLast(new HttpResponseEncoder());
                            pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));
                            pipeline.addLast(new HttpServerHandler()); //添加服务端channel处理逻辑
                        }
                    }).bind(8080)
                    .sync()//阻塞等待服务器的开启
                    .channel().closeFuture().sync();//阻塞等待关闭服务器
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
