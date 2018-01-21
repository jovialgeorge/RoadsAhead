package com.example.mypc.frider.model;

/**
 * Created by MyPc on 10-10-2017.
 */

public class ImageObject {

    String  server_url;
    String   title;
    int imageResourceId;
    int isfav;
    int isturned;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getIsfav() {
        return isfav;
    }

    public void setIsfav(int isfav) {
        this.isfav = isfav;
    }

    public int getIsturned() {
        return isturned;
    }

    public void setIsturned(int isturned) {
        this.isturned = isturned;
    }


    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
