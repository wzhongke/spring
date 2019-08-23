package concurrency.programming.chatper7;

import com.wang.chapter3.UseVolatileRight;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by 王忠珂 on 2016/12/1.
 */
public class AtomicReferenceTest {
    public static AtomicReference<UseVolatileRight.Person> atomicPersonRef
            = new AtomicReference<>();
    private static AtomicIntegerFieldUpdater<User> updater =
            AtomicIntegerFieldUpdater.newUpdater(User.class, "old");
    public static void main(String [] args) {
        User conan = new User("conan", 10);
        System.out.println(updater.getAndIncrement(conan));
        System.out.println(updater.get(conan));
    }

    public static class User {
        private String name;
        public volatile int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
