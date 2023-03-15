package com.example.warehouse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddItemActivityTest
{
    public static final String MESSAGE = "Something"; // key used to pass data using 'intent extras'
    private String name = "";
    private String type = "";
    private String brand = "";
    private String condition = "";
    private String quantity = "";
    private String price = "";
    private String color = "";
    private String comments = "";

    @Test
    public void chooseType()
    {
        String a = "Iphone";

        if(a.equals("Android")) {
            assertEquals("Android", a);
        }
        else {
            assertEquals("Iphone", a);
        }
    }

    @Test
    public void chooseCondition() {
        String b = "Used";
        if(b.equals("New")) {
            assertEquals("New", b);
        }
        else {
            assertEquals("Used", b);
        }
    }

}

