package wang.java8;

/**
 * 方法引用通过方法的名字来指向一个方法。
 * 方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
 * 方法引用使用一对冒号 :: 。
 */
@FunctionalInterface
interface Supplier<T> {
    T get();
}

public class MethodReferenceTest {
}
