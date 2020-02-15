package com.example.ahsmerdeka;

public class Product {
    private int product_id;
    private String productname;
    private String price;

    public Product(){

    }

    public Product(String productname, String price) {
        this.productname = productname;
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
