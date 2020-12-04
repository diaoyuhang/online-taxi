package com.diao.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 16:16 2020/11/4
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client".getBytes()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;

            HttpMethod method = request.method();
            if ("get".equals(method.name().toLowerCase())) {
                QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
                Map<String, List<String>> parameters = decoder.parameters();
                if (parameters != null) {
                    List<String> value = parameters.get("name");
                    System.out.println(value.get(0));

                    String responseHtml = "<html><body>Hello, " + value.get(0) + "</body></html>";
                    byte[] responseBytes = responseHtml.getBytes("UTF-8");
                    int length = responseBytes.length;

                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseBytes));
                    response.headers().set("Content-Type", "text/html;charset=utf-8");
                    response.headers().set("Content-Length", length);

                    ctx.writeAndFlush(response);
                }
            } else {
//                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
////                decoder.offer(request);
//                List<InterfaceHttpData> bodyHttpDatas = decoder.getBodyHttpDatas();
//                Attribute attribute = (Attribute) bodyHttpDatas.get(0);
//                String name = attribute.getName();
//                String value = attribute.getValue();
//
//                String responseHtml="<html><body>Hello, " + value+ "</body></html>";
//                byte[] bytes = responseHtml.getBytes();
//                int length = bytes.length;
//                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
//                response.headers().set("Content-Type","text/html;charset=utf-8");
//                response.headers().set("Content-Length",length);
//
//                ctx.writeAndFlush(response);

            }
        }else if (msg instanceof HttpContent){
            System.out.println("==============");
        }
    }
}
