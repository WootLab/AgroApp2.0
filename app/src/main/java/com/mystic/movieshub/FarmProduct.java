package com.mystic.movieshub;

import java.io.Serializable;

public class FarmProduct implements Serializable {

    private String description;
    private String title;
    private int size;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
