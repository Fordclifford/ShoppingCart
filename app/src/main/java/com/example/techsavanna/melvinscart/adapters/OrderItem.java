package com.example.techsavanna.melvinscart.adapters;

public class OrderItem {

    String amount;
    String name;
    String category;
    String  quatity;

    String FirstName,LastName,City,Street,Phone;

    public OrderItem(String amount, String name, String category, String quatity, String firstName, String lastName, String city, String street, String phone) {
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.quatity = quatity;
        FirstName = firstName;
        LastName = lastName;
        City = city;
        Street = street;
        Phone = phone;
    }

    public OrderItem() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuatity() {
        return quatity;
    }

    public void setQuatity(String quatity) {
        this.quatity = quatity;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
