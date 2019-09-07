package wang.java8.optional;

import java.util.Optional;

public class Main {
    /**
     * Optional 类是一个可以为 null 的容器对象。如果值存在则 isPresent() 方法返回 true，调用 get() 方法会返回该对象
     * Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
     * Optional 类的引入很好的解决空指针异常。
     */

    public static Integer sum(Optional<Integer> a, Optional<Integer> b) {
        System.out.println("第一个参数是否存在:" + a.isPresent());
        System.out.println("第二个参数是否存在：" + b.isPresent());
        // Optional.orElse - 如果值存在，返回它，否则返回默认值
        Integer val1 = a.orElse(0);
        Integer val2 = b.get();
        return val1 + val2;
    }

    public static void main(String[] args) {
        Integer value1 = null;
        Integer value2 = new Integer(10);

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);
        sum(a, b);
    }
}
