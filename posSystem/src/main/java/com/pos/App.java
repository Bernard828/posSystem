package com.pos;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to the POS System!");

        CashRegister register = new CashRegister();
        register.start();
    }
}
