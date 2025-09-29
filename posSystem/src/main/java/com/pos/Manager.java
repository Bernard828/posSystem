package com.pos;

import java.util.Scanner;

public class Manager {
    private Scanner scanner;
    private Inventory inventory;
    private CashRegister cashRegister;

    public Manager(){
        scanner = new Scanner(System.in);
        inventory = new Inventory();
    }


    public void managerMenu() {
        // System.out.print("Enter manager password: ");
        // String password = scanner.nextLine();
        

        // if (!password.equals("admin123")) {
        //     System.out.println("Unauthorized access!");
        //     return;
        // }

        boolean managing = true;
        while (managing) {
            System.out.println("\n----Manager Menu----");
            System.out.println("1. Add New Products");
            System.out.println("2. Restock Product");
            System.out.println("3. Remove Product");
            System.out.println("4. Show Inventory");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    inventory.addNewProduct();
                    break;
                case 2:
                    inventory.restockProduct();
                    break;
                case 3:
                    inventory.removeProduct();
                    break;
                case 4:
                    inventory.showInventory();
                    break;
                case 5:
                    managing = false;
                   // cashRegister.runPOS();
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
