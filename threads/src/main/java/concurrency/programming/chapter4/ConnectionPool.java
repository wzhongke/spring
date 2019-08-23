package concurrency.programming.chapter4;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Created by 王忠珂 on 2016/11/22.
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();
    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i=0; i<initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 连接释放后需要通知，这样其他消费者才能知道有一条连接可用
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 完全超时
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
