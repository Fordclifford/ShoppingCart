package com.example.techsavanna.melvinscart.helper;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productname") private String productname;
    @SerializedName("unit_price") private Double unit_price;
    @SerializedName("qtycarton") private String qtycarton;
    @SerializedName("qtypiece") private String qtypiece;
    @SerializedName("qtyinstock") private String qtyinstock;
    @SerializedName("productcategory") private String productcategory;
    @SerializedName("currency_code") private String currency_code;

    public Product(String productname, Double unit_price, String qtycarton, String qtypiece, String qtyinstock, String productcategory, String currency_code) {
        this.productname = productname;
        this.unit_price = unit_price;
        this.qtycarton = qtycarton;
        this.qtypiece = qtypiece;
        this.qtyinstock = qtyinstock;
        this.productcategory = productcategory;
        this.currency_code = currency_code;
    }
    public Product(String productname, Double unit_price, String qtycarton, String qtypiece) {
        this.productname = productname;
        this.unit_price = unit_price;
        this.qtycarton = qtycarton;
        this.qtypiece = qtypiece;
    }



    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public String getQtycarton() {
        return qtycarton;
    }

    public void setQtycarton(String qtycarton) {
        this.qtycarton = qtycarton;
    }

    public String getQtypiece() {
        return qtypiece;
    }

    public void setQtypiece(String qtypiece) {
        this.qtypiece = qtypiece;
    }

    public String getQtyinstock() {
        return qtyinstock;
    }

    public void setQtyinstock(String qtyinstock) {
        this.qtyinstock = qtyinstock;
    }

    public String getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(String productcategory) {
        this.productcategory = productcategory;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String toString() {
        return "Product id and name: " + productname + " " + unit_price;
    }
}
