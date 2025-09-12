package com.pos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
        loadProductsFromDatabase();
    }

    // -------------STAGE THREE--------------
    public void loadProductsFromDatabase() {
        products.clear();
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet  rs = stmt.executeQuery("SELECT * FROM products")) {

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
        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO products(id, name, price, quantity) VALUES(?, ?, ?, ?)")) {

            pstmt.setString(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        products.add(product);
    }

    public List<Product> getProducts() {
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

    public void listProducts() {
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println(product);
            System.out.println("\n");
        }
    }

    public void showInventory() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Available Products:");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i));
            }
        }
    }

    // ----------STAGE TWO----------------

    // REDUCE STOCK
    public boolean reduceStock(Product product, int qty) {
        if (product.getQuantity() >= qty) {
            int newQty = product.getQuantity() - qty;
            product.setQuantity(product.getQuantity() - qty);

            try (Connection conn = Database.connect();
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

    // -----STAGE ONE ---------
    // public List<Product> getProducts() {
    // return products;
    // }

    // public List<Product> getProducts() {
    // return products;
    // }
}
