package com.pos;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Database.initDatabase();
        System.out.println("Welcome to the POS System!");

        CashRegister CashRegister = new CashRegister();
        CashRegister.start();
    }
}
