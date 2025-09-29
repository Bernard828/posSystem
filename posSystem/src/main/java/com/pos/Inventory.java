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

    // RESTOCK INVENTORY
    public void restockItem(int index, int qty) {
        if (index >= 0 && index < products.size()) {
            Product product = products.get(index);
            product.setQuantity(product.getQuantity() + qty);
        }
    }

    public void restockProduct() {
        showInventory();
        System.out.print("Enter numeric product ID to restock: ");
        String id = scanner.nextLine();

        Product p = getProductById(id);
        if (p == null) {
            System.out.println("Product not found!");
            return;
        }
        System.out.println("How manny " + p.getName() + " to add?");
        int qty = scanner.nextInt();

        // System.out.println("Are you sure you want to restock " + qty + " of " + p.getName() + "? (y/n): ");
        // String confirm = scanner.nextLine();
        // if(!confirm.equalsIgnoreCase("y")){
        //     System.out.println("Cancelled.");
        //     return;
        // }
        p.setQuantity(p.getQuantity() + qty);

        // if (restockChoice > 0 && restockChoice <= getProducts().size()) {
        //     System.out.print("Enter quantity to add: ");
        //     int qty = scanner.nextInt();
        //     scanner.nextLine();
        //     restockItem(restockChoice - 1, qty);
        //     System.out.println("Product restocked successfully!");
        // } else {
        //     System.out.println("Unauthorized access!");
        // }

        // -----------------OLD CODE----------------

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

    public void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        String id = scanner.nextLine();

        Product p = getProductById(id);
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

        removeProduct(id);
        System.out.println("Product removed successfully.");
    }

    public void addNewProduct() {
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
        addProduct(newProduct);
        System.out.println("Product added successfully.");
    }

    // ----------STAGE TWO----------------

    // REDUCE STOCK
    public boolean reduceStockBool(Product product, int qty) {
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
