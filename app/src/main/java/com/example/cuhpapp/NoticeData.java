package com.example.cuhpapp;

public class NoticeData {
    String title,url,key,text,date;
    Boolean flag;

    public NoticeData(String title, String url, String key, String text,String date,Boolean flag) {
        this.title = title;
        this.url = url;
        this.key = key;
        this.text=text;
        this.date = date;
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
    public void setTextView(String text) {
        this.text  = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public NoticeData() {

    }
}
