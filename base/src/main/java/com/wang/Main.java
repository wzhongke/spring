package com.wang;

public class Main {

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
