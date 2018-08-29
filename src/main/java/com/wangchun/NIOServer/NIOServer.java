package com.wangchun.NIOServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Created by Administrator on 2018/8/29.
 */
public class NIOServer implements Runnable{
    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);


    public NIOServer(int port) {
        try {
            //打开多复用器
            selector = Selector.open();
            //打开通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //设置服务器通道为非阻塞方式
            ssc.configureBlocking(false);
            //绑定Socket地址
            ssc.bind(new InetSocketAddress(port));
            //把服务器通道注册到多路复用选择器上,并监听阻塞状态
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server  Start ,port:" +port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true){
            try {
                //让多路复用选择器开始监听
                selector.select();
                //返回所有已经注册到多路复用器上的Selectionkey
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                //遍历所有的keys
                while(keys.hasNext()){
                    SelectionKey key = keys.next();
                    keys.remove();
                    if(key.isValid()) { //如果key的状态是有效的
                        if(key.isAcceptable()) { //如果key是阻塞状态，则调用accept()方法
                            accept(key);
                        }
                        if(key.isReadable()) { //如果key是可读状态，则调用read()方法
                            read(key);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void read(SelectionKey key) {
        System.out.println("当前处于可读状态");
        try {
            //清除缓存区的旧数据
            buffer.clear();
            //获取key对应的SocketChannel通道
            SocketChannel sc = (SocketChannel) key.channel();
            //将通道中的数据放到buffer中去
            int count = sc.read(buffer);
            if(count == -1){
                key.channel().close();
                key.cancel();
                return;
            }
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            //将buffer中的数据写入byte[]中
            buffer.get(bytes);
            //去掉字符串后的空格
            String body = new String(bytes).trim();
            System.out.println("Server：" + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key) {
        System.out.println("当前处于阻塞状态");
        try {
            //拿到key对应的通道
            ServerSocketChannel  ssc = (ServerSocketChannel) key.channel();
            //执行阻塞方法
            SocketChannel sc = ssc.accept();
            //设置阻塞状态为非阻塞
            sc.configureBlocking(false);
            //注册通道到多路复用器上,并设置读取标识
            sc.register(selector,SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Thread(new NIOServer(8379)).start();
    }
}
