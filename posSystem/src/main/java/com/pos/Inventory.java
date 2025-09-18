package com.pos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private List<Product> products;
    private Scanner scanner;

    public Inventory() {
        this.products = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadProductsFromDatabase();
    }

    // -------------STAGE THREE--------------
    public void loadProductsFromDatabase() {
        products.clear();
        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                products.add(new Product(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product) {
        // products.add(product);

        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO products(id, name, price, quantity) VALUES(?, ?, ?, ?)")) {

            pstmt.setString(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.executeUpdate();
            System.out.println("Product added: " + product.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding product: " + e.getMessage());
        }
        products.add(product);
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = Database.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return products;
    }

    public Product getProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null; // not found
    }

    public void showInventory() {
        System.out.println("\n--- Product Inventory ---");
        // System.out.println("\nProduct List:");
        for (Product product : products) {
            System.out.println(product);
            // System.out.println("\n");
        }
    }

    public void startSale() {
        Sale sale = new Sale(); // Start a new sale
        boolean shopping = true;

        while (shopping) {
            showInventory();
            System.out.print("Enter product number (Press 0 to finsih): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                shopping = false;
                sale.printReceipt();
            } else if (choice > 0 && choice <= getProducts().size()) {
                Product selected = getProducts().get(choice - 1);

                System.out.print("Enter quantity: ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                // --------PRODUCT QTY SCALES INVENTORY----------
                if (reduceStock(selected, qty)) {
                    sale.addProduct(selected, qty);
                    System.out.println("Added " + qty + " x " + selected.getName());
                } else {
                    System.out.println("Not enough stock available!");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        }
        // if (products.isEmpty()) {
        // System.out.println("Inventory is empty.");
        // } else {
        // System.out.println("\nShop 'till You Drop:");
        // for (int i = 0; i < products.size(); i++) {
        // System.out.println((i + 1) + ". " + products.get(i));
        // }
        // }
    }

    // ----------STAGE TWO----------------

    // REDUCE STOCK
    public boolean reduceStock(Product product, int qty) {
        if (product.getQuantity() >= qty) {
            int newQty = product.getQuantity() - qty;
            product.setQuantity(product.getQuantity() - qty);

            try (Connection conn = Database.getConnection();
                    PreparedStatement pstmt = conn.prepareStatement(
                            "UPDATE products SET quantity = ? WHERE id = ?")) {
                pstmt.setInt(1, newQty);
                pstmt.setString(2, product.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    // RESTOCK INVENTORY
    public void restockProduct(int index, int qty) {
        if (index >= 0 && index < products.size()) {
            Product product = products.get(index);
            product.setQuantity(product.getQuantity() + qty);
        }
    }

    // REMOVE PRODUCT
    public void removeProduct(String id) {
        products.removeIf(p -> p.getId().equals(id));

        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM  products WHERE id = ?")) {
            pstmt.setString(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Product removed.");
            } else {
                System.out.println("No product found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving products: " + e.getMessage());
        }
    }

    // -----STAGE ONE ---------
    // public List<Product> getProducts() {
    // return products;
    // }
}
