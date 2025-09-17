package com.pos;

import java.util.Scanner;

public class CashRegister {
    private Scanner scanner;
    private Inventory inventory;

    public CashRegister() {
        scanner = new Scanner(System.in);
        inventory = new Inventory();

        if (inventory.getProducts().isEmpty()) {
            inventory.addProduct(new Product("P001", "Apple", 0.99, 10));
            inventory.addProduct(new Product("P002", "Bread", 2.49, 5));
            inventory.addProduct(new Product("P003", "Milk", 3.19, 8));
            inventory.addProduct(new Product("P004", "Soap", 1.50, 12));
            inventory.addProduct(new Product("P005", "Eggs", 4.99, 56));
            inventory.addProduct(new Product("P006", "Cheese", 3.27, 19));
            inventory.addProduct(new Product("P007", "Cereal", 2.59, 20));
        }
    }

    public void start() {
        // -----STAGE FOUR------
        boolean running = true;
        while (running) {
            System.out.println("\n---POS Menu---");
            System.out.println("1. Show Inventory");
            System.out.println("2. Start Sale");
            System.out.println("3. Manager Menu");
            System.out.println("4. Exit");
            System.out.print("Choose option");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showInventory();
                case 2:
                    startSale();
                case 3:
                    managerMenu();
                case 4:
                    running = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void showInventory() {
        System.out.println("\n---Inventory----");
        inventory.listProducts();
    }

    private void startSale() {
        // Sale sale = new Sale(inventory, scanner);
        // sale.start();
        Sale sale = new Sale(); // Start a new sale
        boolean shopping = true;

        while (shopping) {
            inventory.showInventory();
            System.out.print("Enter product number (Press 0 to finsih): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                shopping = false;
                sale.printReceipt();
            } else if (choice > 0 && choice <= inventory.getProducts().size()) {
                Product selected = inventory.getProducts().get(choice - 1);

                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                // --------PRODUCT QTY SCALES INVENTORY----------
                if (inventory.reduceStock(selected, qty)) {
                    sale.addProduct(selected, qty);
                    System.out.println("Added " + qty + " x " + selected.getName());
                } else {
                    System.out.println("Not enough stock available!");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        }

    }

    private void managerMenu() {
        System.out.print("Enter manager password: ");
        String password = scanner.nextLine();

        if (!password.equals("admin123")) {
            System.out.println("Unauthorized access!");
            return;
        }

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
                    addNewProduct();
                case 2:
                    restockProduct();
                case 3:
                    removeProduct();
                case 4:
                    showInventory();
                case 5:
                    managing = false;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addNewProduct() {
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        Product newProduct = new Product(id, name, price, qty);
        inventory.addProduct(newProduct);
        System.out.println("Product added successfully.");
    }

    private void restockProduct() {
        System.out.print("Enter product ID to restock: ");
        int restockChoice = scanner.nextInt();
        scanner.nextLine();

        if (restockChoice > 0 && restockChoice <= inventory.getProducts().size()) {
            System.out.print("Enter quantity to add: ");
            int qty = scanner.nextInt();
            scanner.nextLine();
            inventory.restockProduct(restockChoice - 1, qty);
            System.out.println("Product restocked successfully!");
        } else {
            System.out.println("Unauthorized access!");
        }

        // String id = scanner.nextLine();

        // Product p = inventory.getProductById(id);
        // if (p == null) {
        // System.out.println("Product not found!");
        // return;
        // }

        // System.out.print("Enter quantity to add: ");
        // int qty = scanner.nextInt();
        // scanner.nextLine();

        // p.setQuantity(p.getQuantity() + qty);
        // inventory.updateProduct(p);

        // System.out.println("Product restocked successfuly");
    }

    private void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        String id = scanner.nextLine();

        Product p = inventory.getProductById(id);
        if (p == null) {
            System.out.println("Product not found!");
            return;
        }

        System.out.print("Are you sure you want to remove " + p.getName() + "? (y/n): ");
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Cancelled.");
            return;
        }

        inventory.removeProduct(id);
        System.out.println("Product removed successfully.");
    }
}
// -------STAGE THREE-----
// System.out.println("Welcome to the POS system!");

// // Sale sale = new Sale();
// // System.out.println("Cash Register Started.");
// // inventory.showInventory();

// boolean running = true;

// while (running) {
// System.out.println("\n--- POS MAIN MENU ---");
// System.out.println("1. Show Inventory");
// System.out.println("2. Start Sale");
// //System.out.println("3. Restock Products");
// System.out.println("4. Exit");
// System.out.println("Choose an option: ");

// int option = scanner.nextInt();
// scanner.nextLine();

// switch (option) {
// case 1:
// inventory.showInventory();
// break;

// case 2:
// Sale sale = new Sale(); // Start a new sale
// boolean shopping = true;

// while (shopping) {
// inventory.showInventory();
// System.out.print("Enter product number (Press 0 to finsih): ");
// int choice = scanner.nextInt();
// scanner.nextLine();

// if (choice == 0) {
// shopping = false;
// sale.printReceipt();
// } else if (choice > 0 && choice <= inventory.getProducts().size()) {
// Product selected = inventory.getProducts().get(choice - 1);

// System.out.print("Enter quantity: ");
// int qty = scanner.nextInt();
// scanner.nextLine();

// // --------PRODUCT QTY SCALES INVENTORY----------
// if (inventory.reduceStock(selected, qty)) {
// sale.addProduct(selected, qty);
// System.out.println("Added " + qty + " x " + selected.getName());
// } else {
// System.out.println("Not enough stock available!");
// }
// } else {
// System.out.println("Invalid choice.");
// }
// }
// break;

// case 3:
// System.out.print("Enter admin password: ");
// String password = scanner.nextLine();
// if (password.equals("admin123")) {
// inventory.showInventory();
// System.out.print("Enter product number to restock: ");
// int restockChoice = scanner.nextInt();
// scanner.nextLine();

// if (restockChoice > 0 && restockChoice <= inventory.getProducts().size()) {
// System.out.print("Enter quantity to add: ");
// int qty = scanner.nextInt();
// scanner.nextLine();
// inventory.restockProduct(restockChoice - 1, qty);
// System.out.println("Product restocked successfully!");
// } else {
// System.out.println("Unauthorized access!");
// }
// }
// break;

// case 4:
// running = false;
// System.out.println("Exiting POS System. Goodbye!");
// break;

// default:
// System.out.println("Invalid option. Please try again.");
// }
// -----------STAGE TWO -----------
// inventory.showInventory();

// System.out.print("Select product number (or 0 to finish): ");
// int choice = scanner.nextInt();
// scanner.nextLine();

// if(choice == 0){
// running = false;
// sale.printReceipt();
// }else if (choice > 0 && choice <= inventory.getProducts().size()) {
// Product selected = inventory.getProducts().get(choice - 1);

// System.out.print("Enter quantity: ");
// int qty = scanner.nextInt();
// scanner.nextLine();

// sale.addProduct(selected, qty);
// System.out.println("Added " + qty + " x " + selected.getName());
// } else{
// System.out.println("Invalid choice.");
// }
// ------------STAGE ONE----------------
// if(choice > 0 && choice <= inventory.getProducts().size()){
// Product selected = inventory.getProducts().get(choice - 1);
// System.out.println("You selcted: " + selected);
// } else{
// System.out.println("Invalid choice.");
// }

// System.out.print("Enter product name: ");
// String name = scanner.nextLine();

// System.out.println("You entered: " + name);

// public static void main(String[] args) {
// Scanner scanner = new Scanner(System.in);

// // Create Inventory
// Inventory inventory = new Inventory();
// inventory.addProduct(new Product("P001", "Apple", 0.99, 10));
// inventory.addProduct(new Product("P002", "Bread", 2.49, 5));
// inventory.addProduct(new Product("P003", "Milk", 3.19, 8));

// boolean running = true;

// while (running) {
// System.out.println("\n--- POS System ---");
// inventory.listProducts();
// System.out.println("Start a new sale? (yes/no)");
// String choice = scanner.nextLine();

// if (choice.equalsIgnoreCase("y")) {
// Sale sale = new Sale();
// boolean shopping = true;

// while (shopping) {
// System.out.println("Enter product ID (or 'done' to finish): ");
// String productId = scanner.nextLine();

// if (productId.equalsIgnoreCase("done")) {
// shopping = false;
// sale.printReceipt();
// } else {
// Product product = inventory.getProductById(productId);
// if (product != null) {
// System.out.println("Enter quantity: ");
// int qty = Integer.parseInt(scanner.nextLine());
// sale.addProduct(product, qty);
// } else {
// System.out.println("Invalid product ID.");
// }
// }
// }
// } else {
// running = false;
// System.out.println("POS System shutting down...");
// }
// }
// scanner.close();
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
