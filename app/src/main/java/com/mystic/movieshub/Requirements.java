package com.mystic.movieshub;

import android.net.Uri;

import java.util.List;

public class Requirements {
    private List<Uri> images;
    private String location;
    private String description;
    private String agricTypes;
    private boolean applicationState;
    private boolean eligible;


    public void setApplicationState(boolean applicationState) {
        this.applicationState = applicationState;
    }



    public boolean isApplicationState() {
        return applicationState;
    }
    public Requirements() {
    }

    public List<Uri> getImages() {
        return images;
    }

    public void setImages(List<Uri> images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgricTypes() {
        return agricTypes;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public void setAgricTypes(String agricTypes) {
        this.agricTypes = agricTypes;
    }
}
