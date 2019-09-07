package wang.java8.inter;

interface Inter {
    default void bak(){
        System.out.println("Inter bak");
    }
}

interface Inter1 {
    default void bak() {
        System.out.println("Inter1 bak");
    }
}

abstract class Test {
    private  void test() {}
}

public class Main implements Inter, Inter1{

    /** 两个接口有相同的 default 方法时，子类必须实现 */
    @Override
    public void bak() {
        Inter.super.bak();
    }
}
