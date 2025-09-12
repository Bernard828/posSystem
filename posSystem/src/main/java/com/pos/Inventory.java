package com.pos;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
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
            product.setQuantity(product.getQuantity() - qty);
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
