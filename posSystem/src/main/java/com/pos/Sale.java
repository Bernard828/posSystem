package com.pos;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private List<Product> items;

    public Sale() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            product.setQuantity(product.getQuantity() - quantity); // reduce stock;
            items.add(new Product(product.getId(), product.getName(), product.getPrice(), quantity));
        } else {
            System.out.println("Not enough stock for:" + product.getName());
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : items) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    public void printReceipt() {
        System.out.println("Receipt:");
        for (Product product : items) {
            System.out.println(product.getName() + " x " + product.getQuantity() + " = $"
                    + (product.getPrice() * product.getQuantity()));
        }
        System.out.println("TOTAL: $" + calculateTotal());
    }
}
