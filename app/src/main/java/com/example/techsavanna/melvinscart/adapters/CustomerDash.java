package com.example.techsavanna.melvinscart.adapters;

import java.util.List;

public class CustomerDash {
    String name;
    int photoId;
    private List<CustomerDash> customerDashes;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.

    public CustomerDash(String name, int photoId) {
        this.name = name;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
