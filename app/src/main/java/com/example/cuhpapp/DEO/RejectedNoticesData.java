package com.example.cuhpapp.DEO;

public class RejectedNoticesData {
    String title,url,key,text,date,reason;
    Boolean flag;

    public RejectedNoticesData(String title, String url, String key, String text, String date, String reason, Boolean flag) {
        this.title = title;
        this.url = url;
        this.key = key;
        this.text = text;
        this.date = date;
        this.reason = reason;
        this.flag = flag;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public RejectedNoticesData() {
    }
}
