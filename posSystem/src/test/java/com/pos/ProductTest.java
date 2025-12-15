package com.pos;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProductTest{
    @Test
    void productFieldsareSetCorrectly(){
        Product product = new Product("P001", "Apple", 0.99, 10);

        assertEquals("P001", product.getId());
        assertEquals("Apple", product.getName());
        assertEquals(0.99, product.getPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void reduceQuantityDecreasesStock(){
        Product product = new Product("P002", "Bread", 2.49, 5);

        product.reduceQuantity(2);

        assertEquals(3, product.getQuantity());
    }
}

// public ProductTest {}
