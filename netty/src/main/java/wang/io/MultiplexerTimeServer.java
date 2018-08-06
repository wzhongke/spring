package wang.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop = false;

    /**
     *  初始化多路复用器，绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            // 构造多路复用器
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            // 设置 channel 为异步非阻塞模式
            serverChannel.configureBlocking(false);
            // 绑定端口，并将 backlog 设为 1024
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            // 将 channel 注册到 selector，并监听 SelectionKey.OP_ACCEPT 操作
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop () {
        this.stop = true;
    }

    @Override
    public void run() {
        System.out.println(stop);
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.keys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    handleInput(key);
                    key.cancel();
                    if (key.channel() != null) {
                        key.channel().close();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 多路复用器关闭后，所有注册在上面的 Channel 和 Pipe 等资源都会被自动
            // 注册并关闭，所以不需要重复释放资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleInput(SelectionKey key ) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // Add new connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                // Read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte [] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String (bytes, "UTF8");
                    System.out.println("The time server receive order: " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    // -1表示链路已经关闭，关闭对端链路
                    key.cancel();
                    sc.close();
                } else {
                    ; // 读到 0 字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte [] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}
