package com.pos;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventor(){
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public Product getProductById(String id) {
        for(Product product : products){
            if(product.getId().equals(id)){
                return product;
            }
        }
        return null; //not found
    }

    public void listProducts(){
        System.out.println("Available Products:");
        for(Product product: products){
            System.out.println(product);
        }
    }
}
