package concurrency.programming.chapter4.threadpool;

/**
 * Created by 王忠珂 on 2016/11/23.
 */
public interface ThreadPool<Job extends Runnable> {
    // 执行一个Job，这个Job需要实现Runnable
    void execute(Job job);
    void shutdown();
    void addWorkers(int num);
    void removeWorkers(int num);
    int getJobSize();
}
