package wang.io;

public class TimeClient {

    public static void main (String [] args) {
        new Thread(new TimeClientHandle("127.0.0.1", 8080), "Client").start();
    }
}
