package concurrency.programming.chapter3;

/**
 * Created by 王忠珂 on 2016/11/21.
 */
public class DoubleChecked {
    private static volatile  Instance instance;
    public static Instance getInstance () {
        if (instance == null) {                 // 第一次检查
            synchronized (DoubleChecked.class) { // 加锁
                if (instance == null) {         //  第二次检查
                    instance = new Instance();  // 问题根源在此
                }
            }
        }
        return instance;
    }

    private static class InstanceHolder  {
        public static Instance instance = new Instance();
    }
    public static Instance getInstanceByClass () {
        return InstanceHolder.instance;
    }

    public static void main(String [] args) {
        Thread threadA = new Thread(()->{
           int i = 0;
            long time = System.currentTimeMillis();
            for (; i<1000; i++) {
                DoubleChecked.getInstanceByClass();
            }
            System.out.println(" ThreadA: "+ (System.currentTimeMillis() - time));
        });

        Thread threadB = new Thread(()->{
            int i = 0;
            long time = System.currentTimeMillis();
            for (; i<1000; i++) {
                DoubleChecked.getInstanceByClass();
            }
            System.out.println(" ThreadB: "+ (System.currentTimeMillis() - time));
        });
        threadA.start();
        threadB.start();
    }

}

class Instance {
    Instance(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}