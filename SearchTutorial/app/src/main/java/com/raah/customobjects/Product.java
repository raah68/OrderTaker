package com.raah.customobjects;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public class Product {
    public int product_id;
    private int product_quantity;
    private String product_name;
    private BigDecimal product_price;
    private String product_type;

    // Class Constructor
    public Product(String item_type, String item_name, BigDecimal item_price, int product_id) {
        super();
        this.product_type = item_type;
        this.product_name = item_name;
        this.product_price = item_price;
        this.product_quantity = 1;
        this.product_id = product_id;
    }


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
} // End of Class
