package com.example.techsavanna.melvinscart.adapters;

import com.example.techsavanna.melvinscart.R;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {

    String name;
    int photoId;
    private List<Dashboard> dashboards;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.

    public Dashboard(String name, int photoId) {
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
