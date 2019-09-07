
# 接口的默认方法和静态方法
java8 之前，接口中只能包含抽象方法。如果想要在 `Collection` 接口中增加一个 `spliter` 方法，那么意味着所有实现 `Collection` 的类，都要重新实现该方法。
而接口的默认方法就是为了解决接口的修改与接口实现类不兼容的问题。
```java
public interface Inter {
    default void test() {
        System.out.println("test");
    }
}

public class InterImp implements Inter{
    public static void main(String[] args){
      InterImp test = new InterImp();
      test.test();
    }
}
```

# 函数式接口
java8 最大的变化就是引入了**函数式编程思想**，即函数可以作为另一个函数的参数。
**函数式接口要求有且仅有一个抽象方法，但是可以有若干个默认接口**
使用 `@FunctionalInterface` 注解可以声明一个接口是函数式接口。
```java
@FunctionalInterface
public interface FunctionalInter {
    void say(String message);
    
    default void run() {
        System.out.println("run");
    }
}

FunctionalInter greetService1 = message -> System.out.println("Hello " + message);
```

# lambda 表达式
lambda 表达式即匿名函数，是一段没有函数名的函数体。可以直接作为参数传递给调用者。
lambda 表达式的结构为：
```java
(parameters) -> expression
(parameters) -> { statements; }
```

主要有如下特性：
- 可选类型声明：不需要声明参数类型，编译器可以统一识别参数值
- 可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号
- 可选的大括号：如果主体中只包含一个语句，那么就不需要大括号
- 可选的返回参数：如果主体只有一个表达式返回值则编译器会自动返回值

# 方法引用
方法引用是为了进一步简化lambda表达式。
方法引用使用 `::` 来定义，`::` 的前半部分表示类名或者实例名，后半部分表示方法名，如果是构造方法就使用NEW来表示。

一共有以下几种形式：
- 静态方法引用：`ClassName::methodName`;
- 实例上的实例方法引用：`instanceName::methodName`;
- 超类上的实例方法引用：`super::methodName`;
- 类的实例方法引用：`ClassName:methodName`;
- 构造方法引用 `Class:new`;
- 数组构造方法引用 `TypeName[]::new`
