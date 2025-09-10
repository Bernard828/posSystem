package com.pos;

import java.util.Scanner;

public class CashRegister {
    private Scanner scanner;
    private Inventory inventory;

    public CashRegister() {
        scanner = new Scanner(System.in);
        inventory = new Inventory();

        inventory.addProduct(new Product("P001", "Apple", 0.99, 10));
        inventory.addProduct(new Product("P002", "Bread", 2.49, 5));
        inventory.addProduct(new Product("P003", "Milk", 3.19, 8));
        inventory.addProduct(new Product("P004", "Soap", 1.50, 12));
        inventory.addProduct(new Product("P005", "Eggs", 4.99, 56));
        inventory.addProduct(new Product("P006", "Cheese", 3.27, 19));
        inventory.addProduct(new Product("P007", "Cereal", 2.59, 20));
    }

    public void start() {
        System.out.println("Cash Register Started.");
        Sale sale = new Sale();
        //inventory.showInventory();

        boolean running =  true;

        while(running){
            inventory.showInventory();

            System.out.print("Select product number (or 0 to finish): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 0){
                running = false;
                sale.printReceipt();
            }else if (choice > 0 && choice <= inventory.getProducts().size()) {
                Product selected = inventory.getProducts().get(choice - 1);

                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                sale.addProduct(selected, qty);
                System.out.println("Added " + qty + " x " + selected.getName());
            } else{
                System.out.println("Invalid choice.");
            }
            
            // if(choice > 0 && choice <= inventory.getProducts().size()){
            //     Product selected = inventory.getProducts().get(choice - 1);
            //     System.out.println("You selcted: " + selected);
            // } else{
            //     System.out.println("Invalid choice.");
            // }
        }




        // System.out.print("Enter product name: ");
        // String name = scanner.nextLine();

        // System.out.println("You entered: " + name);
    }

    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);

    //     // Create Inventory
    //     Inventory inventory = new Inventory();
    //     inventory.addProduct(new Product("P001", "Apple", 0.99, 10));
    //     inventory.addProduct(new Product("P002", "Bread", 2.49, 5));
    //     inventory.addProduct(new Product("P003", "Milk", 3.19, 8));

    //     boolean running = true;

    //     while (running) {
    //         System.out.println("\n--- POS System ---");
    //         inventory.listProducts();
    //         System.out.println("Start a new sale? (yes/no)");
    //         String choice = scanner.nextLine();

    //         if (choice.equalsIgnoreCase("y")) {
    //             Sale sale = new Sale();
    //             boolean shopping = true;

    //             while (shopping) {
    //                 System.out.println("Enter product ID (or 'done' to finish): ");
    //                 String productId = scanner.nextLine();

    //                 if (productId.equalsIgnoreCase("done")) {
    //                     shopping = false;
    //                     sale.printReceipt();
    //                 } else {
    //                     Product product = inventory.getProductById(productId);
    //                     if (product != null) {
    //                         System.out.println("Enter quantity: ");
    //                         int qty = Integer.parseInt(scanner.nextLine());
    //                         sale.addProduct(product, qty);
    //                     } else {
    //                         System.out.println("Invalid product ID.");
    //                     }
    //                 }
    //             }
    //         } else {
    //             running = false;
    //             System.out.println("POS System shutting down...");
    //         }
    //     }
    //     scanner.close();
    // }

    // //Create some products
    // Product apple = new Product("P001", "Apple", 0.99, 10);
    // Product bread = new Product("P002", "Bread", 2.49, 5);

    // //Start a sale
    // Sale sale = new Sale();
    // sale.addProduct(apple, 3);
    // sale.addProduct(bread, 1);

    // //Print receipt
    // sale.printReceipt();
}
