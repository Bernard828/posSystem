package com.pos;
public class CashRegister {
    public static void main(String[] args){
        //Create some products
        Product apple = new Product("P001", "Apple", 0.99, 10);
        Product bread = new Product("P002", "Bread", 2.49, 5);

        //Start a sale
        Sale sale = new Sale();
        sale.addProduct(apple, 3);
        sale.addProduct(bread, 1);

        //Print receipt
        sale.printReceipt();
    }
}
