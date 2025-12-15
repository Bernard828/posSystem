package com.pos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryTest{
    private Inventory inventory;
    private Product apple;

    @BeforeEach
    void setUp(){
        inventory = new Inventory();
        apple = new Product("P001", "Apple", 0.99, 10);
        inventory.addProduct(apple);
    }

    @Test
    void addProductIncreasesInventorySize(){
        assertEquals(1, inventory.getProducts().size());
    }

    @Test
    void reduceStockBoolReturnsTrueWhenEnoughStock(){
        boolean result = inventory.reduceStockBol(apple, 20);

        assertFalse(result);
        assertEquals(10, apple.getQuantity());
    }
}
// public class InventoryTest {}