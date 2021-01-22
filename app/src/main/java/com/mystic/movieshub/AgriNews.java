package com.mystic.movieshub;

public class AgriNews {

    private String title;
    private String description;
    private String source;

    public AgriNews(String title, String description, String source) {
        this.title = title;
        this.description = description;
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
