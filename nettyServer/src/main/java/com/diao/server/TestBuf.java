package com.diao.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:04 2020/11/27
 */
public class TestBuf {

    public static void main(String[] args){
        String c="abcd,我太阳你吗";
        ByteBuf buffer = Unpooled.buffer(1024);
        ByteBuf directBuffer = Unpooled.directBuffer();
        ByteBuf byteBuf = Unpooled.wrappedBuffer(c.getBytes());

        System.out.println(buffer.getClass()+"\r\n"+directBuffer.getClass()+"\r\n"+byteBuf.getClass());
    }
}
