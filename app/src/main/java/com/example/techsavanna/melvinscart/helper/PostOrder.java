package com.example.techsavanna.melvinscart.helper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostOrder {
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("customerno")
    @Expose
    private String customerno;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Post{" +
                "subject='" + subject + '\'' +
                ", customer='" + customerno + '\'' +
                ", total=" + total+
                ", id=" + id +
                '}';
    }
}
