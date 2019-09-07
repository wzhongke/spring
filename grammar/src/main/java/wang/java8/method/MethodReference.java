package wang.java8.method;

import java.util.Arrays;
import java.util.List;

public class MethodReference {
    //Supplier是jdk1.8的接口，这里和lamda一起使用了
    public static MethodReference create(final MethodReferenceInter<MethodReference> supplier) {
        return supplier.get();
    }

    public static void collide(final MethodReference car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final MethodReference another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }

    public static void main(String[] args) {
        // 构造器引用：它的语法是Class::new，或者更一般的Class< T >::new实例如下：
        MethodReference reference = MethodReference.create(MethodReference::new);
        // 静态方法引用：它的语法是Class::static_method，实例如下：
        final List<MethodReference> references = Arrays.asList(reference);
        references.forEach(MethodReference::collide);

        // 特定类的任意对象的方法引用：它的语法是Class::method实例如下：
        references.forEach(MethodReference::repair);

        // 特定对象的方法引用：它的语法是instance::method实例如下：
        references.forEach(reference::follow);
    }
}
