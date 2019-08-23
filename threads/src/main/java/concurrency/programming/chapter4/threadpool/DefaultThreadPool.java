package concurrency.programming.chapter4.threadpool;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by 王忠珂 on 2016/11/23.
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    // 线程最大限制数
    private static int maxWorkerNumber = 10;
    // 线程池默认的数量
    private static int defaultWorkerNumbers = 5;
    // 线程池最小数量
    private static int minWorkerNumbers = 1;
    // 工作列表
    private final LinkedList<Job> jobs  = new LinkedList<Job>();
    // 工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    // 工作者线程的数量
    private int workerNum = defaultWorkerNumbers;
    // 线程编号
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorkers(defaultWorkerNumbers);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > maxWorkerNumber ? maxWorkerNumber :
                num < minWorkerNumbers ? minWorkerNumbers : num;
        initializeWorkers(workerNum);
    }

    public DefaultThreadPool(int defaultWorkerNumber, int maxWorkerNumber, int minWorkerNumber) {
        this.maxWorkerNumber = maxWorkerNumber;
        this.minWorkerNumbers = minWorkerNumber;
        workerNum = defaultWorkerNumber > maxWorkerNumber ? maxWorkerNumber :
                defaultWorkerNumber < minWorkerNumbers ? minWorkerNumbers : defaultWorkerNumber;
        initializeWorkers(workerNum);
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker: workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > maxWorkerNumber) {
                num = maxWorkerNumber - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count ++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    private void initializeWorkers (int num) {
        for (int i=0; i<num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    class Worker implements Runnable {
        // 是否工作
        private volatile boolean running = true;
        public void run() {
            while (running){
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    // 取出一个Job
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
