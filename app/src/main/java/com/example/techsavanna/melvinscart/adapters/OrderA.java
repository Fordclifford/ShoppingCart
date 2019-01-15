package com.example.techsavanna.melvinscart.adapters;

import com.google.gson.annotations.SerializedName;

public class OrderA {

    @SerializedName("order_name") private String order_name;
    @SerializedName("order_unit_price") private String order_unit_price;
    @SerializedName("qtycarton") private String qtycarton;
    @SerializedName("qtypiece") private String qtypiece;

    public OrderA() {
        this.order_name = order_name;
        this.order_unit_price = order_unit_price;
        this.qtycarton = qtycarton;
        this.qtypiece = qtypiece;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getOrder_unit_price() {
        return order_unit_price;
    }

    public void setOrder_unit_price(String order_unit_price) {
        this.order_unit_price = order_unit_price;
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
    public String toString() {
        return  order_name + "," + order_unit_price+","+qtycarton+","+qtypiece;
    }
}
