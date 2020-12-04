package com.diao.server.version1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.Scanner;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 10:51 2020/11/9
 */
public class Client {

    ChannelHandlerContext ctx = null;

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.connect();
    }

    public void connect() throws InterruptedException {
        //客户端不需要像服务端一样，就收连接，所以只需要一个线程池
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture future = bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer(this)) //通道初始化
                .connect("127.0.0.1", 8080);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess())
                    System.out.println("连接成功");
                else
                    System.out.println("连接失败");
            }
        });

        future.sync();//阻塞等待连接成功

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            boolean flag = true;
            while (flag) {
                String s = scanner.nextLine();
                this.ctx.writeAndFlush(new MyMessage(s, s.getBytes().length));

                if ("exit".equals(s.trim())) {
                    flag = false;
                }
            }
        }).start();
        future.channel().closeFuture().sync();
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    private Client client;

    public ClientChannelInitializer(Client client) {
        this.client = client;
    }

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MessageCodec());

        pipeline.addLast(new ClientChannelInboundHandlerAdapter(this.client));
    }
}

class ClientChannelInboundHandlerAdapter extends SimpleChannelInboundHandler<MyMessage> {
    private Client client;

    public ClientChannelInboundHandlerAdapter(Client client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        client.ctx = ctx;
    }

    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
        System.out.println(msg);
        if (msg != null)
            ReferenceCountUtil.release(msg);
    }
}

