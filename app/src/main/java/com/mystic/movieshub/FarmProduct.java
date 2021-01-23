package com.mystic.movieshub;

import java.io.Serializable;

public class FarmProduct implements Serializable {

    private String description;
    private String title;
    private int size;
    private User user;
    private String image;

    public FarmProduct(String description){
        this.description = description;
    }

    public String getDescriptrion() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
