package com.example.cuhpapp.Teacher;

public class RejectedEbooksData {
    String title,url,key,text,reason;

    public RejectedEbooksData(String title, String url, String key, String text, String reason) {
        this.title = title;
        this.url = url;
        this.key = key;
        this.text = text;
        this.reason = reason;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RejectedEbooksData() {
    }
}
