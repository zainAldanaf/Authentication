package com.example.authentication.Module;


public class Topics {
    public String getTopic_title() {
        return topic_title;
    }

    public void setTopic_title(String topic_title) {
        this.topic_title = topic_title;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String topic_title;

    public Topics(String topic_title, String image) {
        this.topic_title = topic_title;
        this.image = image;
    }

    private String topic_content;
    private String image;

}