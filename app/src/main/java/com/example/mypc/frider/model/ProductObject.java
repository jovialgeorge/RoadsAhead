package com.example.mypc.frider.model;

/**
 * Created by MyPc on 15-09-2017.
 */

public class ProductObject {

    public  String ProductName;
    public  String ProuductServerUrl;
    public  String pid;
    public  String description;
    public  String prize;
    public  String size;
    public  String color;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {

        this.ProductName = productName;
    }

    public String getProuductServerUrl() {
        return ProuductServerUrl;
    }

    public void setProuductServerUrl(String prouductServerUrl) {
        this.ProuductServerUrl = prouductServerUrl;
    }
}
