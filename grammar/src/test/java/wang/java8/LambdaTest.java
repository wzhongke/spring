package wang.java8;

import org.junit.Test;

/**
 * Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
 * 使用 Lambda 表达式可以使代码变的更加简洁紧凑。
 */
public class LambdaTest {

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    @Test
    /**
      可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。 () -> 5
      可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。 x -> 2 * x
      可选的大括号：如果主体包含了一个语句，就不需要使用大括号。 (x, y) -> x – y
      可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
     */
    public void testLambda() {
        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        MathOperation addition1 = Integer::sum;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> { return a * b; };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + operate(10, 5, addition));
        System.out.println("10 - 5 = " + operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) ->
                System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");

    }

    interface Converter<T1, T2> {
        void convert(int i);
    }
    
    /**
     * lambda 表达式只能引用标记了 final 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量
     */
    @Test
    public void testScope() {
        final int num = 1;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);  // 输出结果为 3
    }
}
