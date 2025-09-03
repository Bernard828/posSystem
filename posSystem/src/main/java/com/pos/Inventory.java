package com.pos;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    // public List<Product> getProducts() {
    //     return products;
    // }

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
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
        }
    }

    public void showInventory(){
        if(products.isEmpty()){
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Available Product:");
            for (int i = 0; i <products.size(); i++){
                System.out.println((i + 1) + ". " + products.get(i));
            }
        }
    }
    
    public List<Product> getProducts() {
        return products;
    }
}
