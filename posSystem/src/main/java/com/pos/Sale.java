package com.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Sale {
    private Map<Product, Integer> items; //product + quantity

    public Sale() {
        //this.items = new ArrayList<>();
        items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
       if(items.containsKey(product)){
        items.put(product, items.getOrDefault(product, 0) + quantity);
       } else {
        items.put(product, quantity);
       }
        // if (product.getQuantity() >= quantity) {
        //     product.setQuantity(product.getQuantity() - quantity); // reduce stock;
        //     items.add(new Product(product.getId(), product.getName(), product.getPrice(), quantity));
        // } else {
        //     System.out.println("Not enough stock for:" + product.getName());
        // }
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()){
            total += entry.getKey().getPrice() * entry.getValue();
        }
        // for (Product product : items) {
        //     total += product.getPrice() * product.getQuantity();
        // }
        return total;
    }

    public void printReceipt() {
        System.out.println("\n--- Receipt ---");
        for(Map.Entry<Product, Integer> entry : items.entrySet()){
            Product product = entry.getKey();
            int qty = entry.getValue();
            double lineTotal = product.getPrice() * qty;
            System.out.println(product.getName() + " x" + qty + " = $" + lineTotal);
        }
        System.out.println("----------");
        System.out.println("TOTAL: $" + getTotal());
        System.out.println("Thank you for shopping!\n");
        // for (Product product : items) {
        //     System.out.println(product.getName() + " x " + product.getQuantity() + " = $"
        //             + (product.getPrice() * product.getQuantity()));
        // }
        // System.out.println("TOTAL: $" + calculateTotal());
    }
}
