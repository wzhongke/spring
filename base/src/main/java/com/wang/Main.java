package com.wang;

class Submain {
    Submain() {
        System.out.println("submain");
    }
}

public class Main extends Submain {

    Main() {
        super();
        System.out.println("main");
    }
    Main(Inter inter) {
        System.out.println("Inter");
    }

    Main(InterImpl inter) {
        System.out.println("InterImpl");
    }

    public static void main(String[] args) {
        Inter inter = new InterImpl();
        InterImpl interImpl = new InterImpl();
        new Main(inter);
        new Main(interImpl);
    }
}
