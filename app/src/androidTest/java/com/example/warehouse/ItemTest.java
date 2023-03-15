package com.example.warehouse;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.warehouse.model.Item;


class ItemTest  {

    @Test
    public void testToString() {
        Item item = new Item("a","b","c","d","e","f","g","h");
        assertEquals("c",item.getCondition());
    }
}