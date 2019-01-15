package com.example.techsavanna.melvinscart.helper;

import com.google.gson.annotations.SerializedName;

public class Contact  {
    @SerializedName("firstname") private String FirstName;
    @SerializedName("lastname") private String LastName;
    @SerializedName("mailingcity") private String City;
    @SerializedName("phone") private String Phone;
    @SerializedName("otherstreet") private String Street;

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getCity() {
        return City;
    }

    public String getPhone() {
        return Phone;
    }

    public String getStreet() {
        return Street;
    }
}
