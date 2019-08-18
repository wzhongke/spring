package concurrency.programming.chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 王忠珂 on 2016/11/20.
 */
public class UseVolatileRight {

    /** #模式1：状态标志
     * 实现 volatile 变量的规范使用仅仅是使用一个布尔状态标志，用于指示发生了一个重要的一次性事件
     */
    volatile boolean shutdownReuqested;
    public void shutdow(){
        shutdownReuqested = true;
    }
    public void doWork() {
        while (!shutdownReuqested) {
            // do stuff
        }
    }

    /**
     * #模式2：一次性安全发布
     */
    class Flooble{}
    class BackgroundFloobleLoader {
        volatile Flooble theFlooble;

        public void initInBackground() {
            // do lots of stuff
            theFlooble = new Flooble();  // this is the only write to theFlooble
        }
    }

    public class SomeOtherClass {
        BackgroundFloobleLoader floobleLoader = new BackgroundFloobleLoader();
        public void doWork() {
            while (true) {
                // do some stuff...
                // use the Flooble, but only if it is ready
                if (floobleLoader.theFlooble != null) {
                    //  doSomething(floobleLoader.theFlooble);
                }
            }
        }
    }


    /**
     * “volatile bean” 模式
     * 在 volatile bean 模式中，JavaBean 的所有数据成员都是 volatile 类型的，并且 getter 和 setter 方法必须非常普通
     * —— 除了获取或设置相应的属性外，不能包含任何逻辑。此外，对于对象引用的数据成员，引用的对象必须是有效不可变的。
     * （这将禁止具有数组值的属性，因为当数组引用被声明为 volatile 时，只有引用而不是数组本身具有 volatile 语义）。
     * 对于任何 volatile 变量，不变式或约束都不能包含 JavaBean 属性
     */
    public class Person {
        private volatile String firstName;
        private volatile String lastName;
        private volatile int age;

        public Person(){}
        public Person(String firstName, int age) {
            this.firstName = firstName;
            this.age = age;
        }

        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public int getAge() { return age; }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
