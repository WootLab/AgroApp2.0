package com.mystic.movieshub;

import java.io.Serializable;

public class AgriNews implements Serializable {

    private String title;
    private String description;
    private String source;
    private String newsId;

    public AgriNews(){

    }
    public AgriNews(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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
